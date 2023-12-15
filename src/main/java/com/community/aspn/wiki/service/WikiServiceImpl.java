package com.community.aspn.wiki.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.community.aspn.menu.mapper.WikiMenuMapper;
import com.community.aspn.menu.service.WikiMenuService;
import com.community.aspn.pojo.sys.CommunityMenu;
import com.community.aspn.pojo.sys.WikiMenu;
import com.community.aspn.pojo.wiki.Wiki;
import com.community.aspn.pojo.wiki.WikiHis;
import com.community.aspn.util.mino.MinoIOComponent;
import com.community.aspn.wiki.mapper.WikiHisMapper;
import com.community.aspn.wiki.mapper.WikiMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class WikiServiceImpl implements WikiService{

    @Resource
    WikiMapper wikiMapper;

    @Resource
    WikiHisMapper wikiHisMapper;

    @Resource
    MinoIOComponent minoIOComponent;

    @Resource
    WikiMenuMapper wikiMenuMapper;

    @Resource
    WikiMenuService wikiMenuService;

    /**
     * @Author nanguangjun
     * @Description // save wiki history and if it active is ture save wiki and active wikiHis
     * @Date 17:21 2021/4/19
     * @Param [params]
     * @return int
     **/
    @Override
    public WikiHis saveWikiHis(WikiHis wikiHis, Boolean flag) {
        WikiHis savedWikiHis = null;
        //if it has wikiHis ID update
        if(wikiHis.getId()!= null && 0 == wikiHis.getHisYn()){
            wikiHis.setUpdateTime(new Date());
            wikiHis.setUpdateId(wikiHis.getMemberId());
            wikiHisMapper.updateById(wikiHis);
            savedWikiHis = wikiHis;
        }else {
            wikiHis.setRegisterId(wikiHis.getMemberId());
            wikiHis.setRegisterTime(new Date());
            wikiHis.setUpdateTime(new Date());
            wikiHis.setUpdateId(wikiHis.getMemberId());
            wikiHis.setHisYn(0);
            if(wikiHis.getWikiId() == null){ // if this is new kiki
                Wiki wiki = this.insertWiki(wikiHis);// insert wiki before wikiHis
                wikiHis.setWikiId(wiki.getId()); // set wikiId from wiki master
            }
            wikiHisMapper.insert(wikiHis); //save wikiHis
            savedWikiHis = wikiHis;
        }
        //active wiki his
        if(flag){ //if this is new wiki
            this.activeWikiHis(savedWikiHis);
        }
        return savedWikiHis;
    }

    /**
     * @Author nanguangjun
     * @Description // save wiki master
     * @Date 14:26 2021/4/20
     * @Param [wikiHis]
     * @return com.community.aspn.pojo.wiki.Wiki
     **/
    @Override
    public Wiki insertWiki(WikiHis wikiHis) {
        Wiki wiki = new Wiki();
        wiki.setMenuId(wikiHis.getMenuId());
        wiki.setPicture(wikiHis.getPicture());
        wiki.setTitle(wikiHis.getTitle());
        wiki.setHisId(wikiHis.getId());
        wiki.setRegisterTime(new Date());
        wiki.setRegisterId(wikiHis.getMemberId());
        Integer i = wikiMapper.addWiki(wiki);
        if(i >0){
            Integer id = wiki.getId();
            wiki.setId(id);
        }
        return wiki;

    }

    /**
     * @Author nanguangjun
     * @Description // active wikiHis
     * @Date 10:07 2021/4/20
     * @Param [wikiHis]
     * @return com.community.aspn.pojo.wiki.Wiki
     **/
    @Override
    public Wiki activeWikiHis(WikiHis wikiHis){
        Wiki wiki = new Wiki();
        Wiki wikiResponse = null;
        wiki.setTitle(wikiHis.getTitle());
        wiki.setMenuId(wikiHis.getMenuId());
        wiki.setHisId(wikiHis.getId());
        if(wikiHis.getPicture() != null ){
            wiki.setPicture(wikiHis.getPicture());
        }
        wikiHis.setHisYn(1);
        wikiHisMapper.updateById(wikiHis); //update wikiHis his_yn
        //if it has wiki id update wiki else new wiki
        if(wikiHis.getWikiId() != null){
            wiki.setId(wikiHis.getWikiId());
            wiki.setUpdateId(wikiHis.getMemberId());
            wiki.setUpdateTime(new Date());
            wikiMapper.updateById(wiki); //update wiki his_id
            wikiResponse = wikiMapper.selectById(wikiHis.getWikiId());
        }
        return wikiResponse;
    }

    /**
     * @Author nanguangjun
     * @Description // select wiki history by wiki history_id
     * @Date 17:29 2021/4/19
     * @Param [wikiHis]
     * @return com.community.aspn.pojo.wiki.WikiHis
     **/
    @Override
    public WikiHis selectWikiHisByID(Integer id,String remoteAddr) {
        WikiHis wikiHis = wikiHisMapper.selectById(id);
        //判断是否是active wiki 如果是active wiki 就把ID删掉
        String content = minoIOComponent.afterGetContentFromDBToFront(wikiHis.getContent(), remoteAddr);
        wikiHis.setContent(content);
        if(wikiHis.getPicture() != null){
            String picture = minoIOComponent.afterGetContentFromDBToFront(wikiHis.getPicture(), remoteAddr);
            wikiHis.setPicture(picture);
        }
        return wikiHis;
    }

    /**
     * @Author nanguangjun
     * @Description //wiki list select
     * @Date 17:20 2021/4/19
     * @Param [params]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public Map<String, Object> selectPageList(Map<String,Integer> params,String remoteAddr) {
        Map<String, Object> result = new HashMap<>(); //最后返回值
        int menuId = params.get("menuId");
        int page = params.get("page");
        int size = params.get("itemsPerPage");
        ArrayList<Integer> authorityWikiList = wikiMenuService.getAuthorityMenus(params.get("authority"));
        List<Integer> menuList = this.getMenuList(menuId);
        menuList.retainAll(authorityWikiList);
        Map<String,Object> totalMapArgs = new HashMap<>();
        totalMapArgs.put("list",menuList);
        Integer total = wikiMapper.selectWikiListCount(totalMapArgs); //select total
        int pages = total%size==0 ? total/size : total/size+1;

        //分页查询传参
        Map<String,Object> args = new HashMap<>();
        args.put("list",menuList);
        args.put("page",(page-1)*size);
        args.put("size",size);
        List<Map<String, Object>> list = wikiMapper.selectWikiList(args);
        //图片地址处理
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).get("picture") != null){
                String picture =
                        minoIOComponent.afterGetContentFromDBToFront(list.get(i).get("picture").toString(),remoteAddr);
                list.get(i).put("picture",picture);
            }else {
                list.get(i).put("picture","");
            }
        }
        result.put("wikis",list); //数据
        result.put("page",page); //当前页面
        result.put("pages",pages); //总页数
        return result;
    }

    /**
     * @Author nanguangjun
     * @Description // menu id list
     * @Date 16:11 2021/5/20
     * @Param [wiki]
     * @return java.util.List<java.lang.Integer>
     **/
    private List<Integer> getMenuList(Integer menuId){
        ArrayList<Integer> result = new ArrayList<Integer>();
        WikiMenu wikiMenu = wikiMenuMapper.selectById(menuId);
        result.add(menuId);
        if(wikiMenu.getTier().equals(3)){
            return result;
        //2级菜单
        }else if(wikiMenu.getTier().equals(2)){
            QueryWrapper<WikiMenu> secondQuery = new QueryWrapper<>();
            secondQuery.eq("tier",3).eq("father",menuId);
            List<WikiMenu> wikiMenus = wikiMenuMapper.selectList(secondQuery);
            //把所有的 子菜单得id 放进去
            for (int i = 0; i < wikiMenus.size(); i++) {
                result.add(wikiMenus.get(i).getId());
            }
        //1级菜单
        }else {
            ArrayList<Integer> list = wikiMenuMapper.selectUnderFirstMenu(menuId);
            //把所有子菜单得id 放进去
            for (int i = 0; i < list.size(); i++) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    /**
     * @Author nanguangjun
     * @Description // wiki main page
     * @Date 16:26 2021/4/21
     * @Param [count, remoteAddr]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> wikiMainList(Map<String,Object> params) {
        ArrayList<Integer> authorityMenuList = wikiMenuService.getAuthorityMenus(Integer.parseInt(params.get("authority").toString()));
        params.put("list",authorityMenuList);
        List<Map<String, Object>> wikis = wikiMapper.selectWikiMainList(params);
        String p = "";
        //图片处理
        for (int i = 0; i < wikis.size(); i++) {
            if(wikis.get(i).get("picture") != null){
                p = minoIOComponent.afterGetContentFromDBToFront(wikis.get(i).get("picture").toString(),
                        params.get("remoteAddr").toString());
                wikis.get(i).put("picture",p);
            }
        }
        return wikis;
    }


    /**
     * @Author nanguangjun
     * @Description // wiki detail page return wikiHis
     * @Date 13:56 2021/4/21
     * @Param [id is wiki id, remoteAddr]
     * @return com.community.aspn.pojo.wiki.WikiHis
     **/
    @Override
    public WikiHis selectWikiDetail(Integer id, String remoteAddr) {
        Wiki wiki = wikiMapper.selectById(id);
        WikiHis wikiHis = wikiHisMapper.selectById(wiki.getHisId());
        String content = minoIOComponent.afterGetContentFromDBToFront(wikiHis.getContent(), remoteAddr);
        if(wikiHis.getPicture() != null){
            String picture = minoIOComponent.afterGetContentFromDBToFront(wikiHis.getPicture(), remoteAddr);
            wikiHis.setPicture(picture);
        }
        wikiHis.setContent(content);
        return wikiHis;
    }

    /**
     * @Author nanguangjun
     * @Description // wiki profile page select wikiHis by memberId
     * @Date 10:16 2021/4/22
     * @Param [memberId]
     * @return java.util.List<com.community.aspn.pojo.wiki.WikiHis>
     **/
    @Override
    public List<Map<String, Object>> selectWikiHisProfile(Integer memberId) {
        List<Map<String, Object>> list = wikiMapper.selectWikiHisProfile(memberId);
        return list;
    }

    /**
     * @Author nanguangjun
     * @Description //wiki detail page select wiki history list dialog
     * @Date 17:18 2021/4/27
     * @Param [wikiId, remoteAddr]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> selectWikiHisList(Integer wikiId, String remoteAddr) {
        List<Map<String, Object>> list = wikiMapper.selectWikiHisList(wikiId);
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).get("picture")!=null){
                String picture = minoIOComponent.afterGetContentFromDBToFront(list.get(i).get("picture").toString(), remoteAddr);
                list.get(i).put("picture",picture);
            }
        }
        return list;
    }

    /**
     * @Author nanguangjun
     * @Description //Back to the past wiki
     * @Date 13:52 2021/4/28
     * @Param [wikiHis]
     * @return com.community.aspn.pojo.wiki.WikiHis
     **/
    @Override
    public void backToThePastWikiHis(WikiHis wikiHis) {
        Integer wikiId = wikiHis.getWikiId();
        Wiki wiki = wikiMapper.selectById(wikiId);
        WikiHis his = wikiHisMapper.selectById(wikiHis.getId());
        //setting wiki
        wiki.setPicture(his.getPicture());
        wiki.setTitle(his.getTitle());
        wiki.setHisId(his.getId());
        wiki.setUpdateTime(new Date());
        wiki.setUpdateId(wikiHis.getUpdateId());
        wikiMapper.updateById(wiki);
    }

    /**
     * @Author nanguangjun
     * @Description //TODO
     * @Date 14:59 2021/4/22
     * @Param [id]
     * @return void
     **/
    @Override
    public void deleteWikiHistoryById(Integer id) {
        wikiHisMapper.deleteById(id);
    }

    /**
     * @Author nanguangjun
     * @Description // get menuId by wiki ID
     * @Date 17:43 2021/6/1
     * @Param [id]
     * @return java.lang.Integer
     **/
    @Override
    public Integer getWikiMenuId(Integer id) {
        return wikiMapper.selectById(id).getMenuId();
    }


    /**
     * @Author nanguangjun
     * @Description // select wikiEdited list by member id
     * @Date 15:08 2021/6/2
     * @Param [memberId]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> selectWikiEditedProfile(Integer memberId,String remoteAddr) {
        List<Map<String, Object>> list = wikiMapper.selectWikiEditedProfile(memberId);
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).get("picture")!=null){
                String picture = minoIOComponent.afterGetContentFromDBToFront(list.get(i).get("picture").toString(), remoteAddr);
                list.get(i).put("picture",picture);
            }
        }
        return list;
    }


    /**
     * @Author nanguangjun
     * @Description //
     * @Date 13:41 2021/8/20
     * @Param [params]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> selectWikiTemplate(Map<String, Object> params) {
        ArrayList<Integer> authorityMenuList = wikiMenuService.getAuthorityMenus(Integer.parseInt(params.get("authority").toString()));
        params.put("list",authorityMenuList);
        List<Map<String, Object>> wikis = wikiMapper.selectWikiTemplate(params);
        String p = "";
        //图片处理
        for (int i = 0; i < wikis.size(); i++) {
            if(wikis.get(i).get("picture") != null){
                p = minoIOComponent.afterGetContentFromDBToFront(wikis.get(i).get("picture").toString(),
                        params.get("remoteAddr").toString());
                wikis.get(i).put("picture",p);
            }
        }
        return wikis;
    }

    /**
     * @Author nanguangjun
     * @Description // wiki template total
     * @Date 13:42 2021/8/20
     * @Param [authority]
     * @return java.lang.Integer
     **/
    @Override
    public Integer selectWikiTemplateCount(Integer authority) {
        ArrayList<Integer> authorityMenuList = wikiMenuService.getAuthorityMenus(authority);
        Map<String, Object> param = new HashMap<>();
        param.put("list",authorityMenuList);
        return wikiMapper.selectWikiTemplateCount(param);
    }

    /**
     * @Author nanguangjun
     * @Description //获取编辑WIKI的人们。
     * @Date 13:41 2021/9/23
     * @Param [params]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> selectWikiHisMembers(Map<String, Object> params) {
        List<Map<String, Object>> result = wikiMapper.selectWikiHisMembers(Integer.parseInt(params.get("wikiId").toString()));
        //图片处理
        String p = "";
        for (int i = 0; i < result.size(); i++) {
            if(result.get(i).get("picture") != null){
                p = minoIOComponent.afterGetContentFromDBToFront(result.get(i).get("picture").toString(),
                        params.get("remoteAddr").toString());
                result.get(i).put("picture",p);
            }
        }
        return result;
    }

    /**
     * 위키 평점
     * @param param
     */
    @Override
    public void wikiRating(HashMap<String, Object> param) {
        wikiMapper.saveWikiRating(param);
    }


}
