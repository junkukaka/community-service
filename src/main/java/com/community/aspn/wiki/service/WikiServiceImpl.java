package com.community.aspn.wiki.service;


import com.community.aspn.pojo.wiki.Wiki;
import com.community.aspn.pojo.wiki.WikiHis;
import com.community.aspn.util.mino.MinoIOComponent;
import com.community.aspn.wiki.mapper.WikiHisMapper;
import com.community.aspn.wiki.mapper.WikiMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class WikiServiceImpl implements WikiService{

    @Resource
    WikiMapper wikiMapper;

    @Resource
    WikiHisMapper wikiHisMapper;

    @Resource
    MinoIOComponent minoIOComponent;

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
            wikiHis.setUpdateId(wikiHis.getMenuId());
            wikiHisMapper.updateById(wikiHis);
            savedWikiHis = wikiHis;
        }else {
            wikiHis.setRegisterId(wikiHis.getMenuId());
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
    public List<Map<String, Object>> wikiList(Wiki wiki, String remoteAddr) {
        String p = "";
        List<Map<String, Object>> wikis = wikiMapper.selectWikiList(wiki);
        //图片处理
        for (int i = 0; i < wikis.size(); i++) {
            if(wikis.get(i).get("picture") != null){
                p = minoIOComponent.afterGetContentFromDBToFront(wikis.get(i).get("picture").toString(), remoteAddr);
                wikis.get(i).put("picture",p);
            }
        }
        return wikis;
    }

    /**
     * @Author nanguangjun
     * @Description // wiki main page
     * @Date 16:26 2021/4/21
     * @Param [count, remoteAddr]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> wikiMainList(Integer count, String remoteAddr) {
        List<Map<String, Object>> wikis = wikiMapper.selectWikiMainList(count);
        String p = "";
        //图片处理
        for (int i = 0; i < wikis.size(); i++) {
            if(wikis.get(i).get("picture") != null){
                p = minoIOComponent.afterGetContentFromDBToFront(wikis.get(i).get("picture").toString(), remoteAddr);
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


}
