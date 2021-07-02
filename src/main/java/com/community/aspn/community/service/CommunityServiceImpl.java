package com.community.aspn.community.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.community.aspn.community.mapper.CommunityMapper;
import com.community.aspn.menu.mapper.CommunityMenuMapper;
import com.community.aspn.pojo.community.Community;
import com.community.aspn.pojo.sys.CommunityMenu;
import com.community.aspn.util.mino.MinIOFileUtil;
import com.community.aspn.util.mino.MinoIOComponent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class CommunityServiceImpl implements CommunityService{

    @Resource
    CommunityMapper communityMapper;

    @Resource
    CommunityMenuMapper communityMenuMapper;

    @Resource
    MinoIOComponent minoIOComponent;


    /**
     * @Author nanguangjun
     * @Description // 写帖子
     * @Date 13:49 2021/1/26
     * @Param [community]
     * @return int
     **/
    @Override
    public int insertCommunity(Community community) {
        community.setRegisterTime(new Date());
        community.setRegisterId(community.getMemberId());
        Community c = this.mdYnContent(community);
        communityMapper.insert(c);
        return c.getId();
    }

    /**
     * @Author nanguangjun
     * @Description // 修改帖子
     * @Date 13:50 2021/1/26
     * @Param [community]
     * @return int
     **/
    @Override
    public int updateCommunity(Community community) {
        community.setUpdateTime(new Date());
        Community c = this.mdYnContent(community);
        return communityMapper.updateById(c);
    }

    /**
     * @Author nanguangjun
     * @Description // 判断是不是mdEditor
     * @Date 16:34 2021/4/29
     * @Param [community]
     * @return com.community.aspn.pojo.community.Community
     **/
    public Community mdYnContent(Community community){
        String content = community.getContent();
        //
        if(community.getMdYn() == 1){
            //替换图片内容
            String DBContent = minoIOComponent.beForeFileSaveInDB(content);
            community.setContent(DBContent);
        }else{
            //是否有截图，如果有截图，处理截图文件。
            if(MinIOFileUtil.ifBase64RegexMatcher(content)){
                String newContent = minoIOComponent.base64RegexReplace(content);
                community.setContent(minoIOComponent.beForeFileSaveInDB(newContent));
            }
        }
        return community;
    }

    /**
     * @Author nanguangjun
     * @Description // 根据ID 查找帖子
     * @Date 13:50 2021/1/26
     * @Param [id]
     * @return com.community.aspn.pojo.community.Community
     **/
    @Override
    public Community selectCommunityById(Integer id) {

        return communityMapper.selectById(id);
    }

    /**
     * @Author nanguangjun
     * @Description //删除帖子
     * @Date 13:51 2021/1/26
     * @Param [id]
     * @return int
     **/
    @Override
    public int deleteCommunityById(Integer id) {
        return communityMapper.deleteById(id);
    }

    @Override
    public List<Community> selectAll(Map<String,Object> params) {
        QueryWrapper<Community> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_id",params.get("menuId"));
        return communityMapper.selectList(queryWrapper);
    }


    /**
     * @Author nanguangjun
     * @Description //分页查询
     * @Date 10:04 2021/3/16
     * @Param [params]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String,Object> selectPageList(Map<String, Integer> params,HttpServletRequest request){
        Map<String, Object> result = new HashMap<>(); //最后返回值
        int menuId = params.get("menuId");
        int page = params.get("page");
        int size = params.get("itemsPerPage");
        List<Integer> menuList = this.getMenuList(menuId);
        Map<String,Object> totalMapArgs = new HashMap<>();
        totalMapArgs.put("list",menuList);
        Integer total = communityMapper.selectCommunityListCount(totalMapArgs); //select total
        int pages = total%size==0 ? total/size : total/size+1;

        //分页查询传参
        Map<String,Object> args = new HashMap<>();
        args.put("list",menuList);
        args.put("page",(page-1)*size);
        args.put("size",size);
        List<Map<String, Object>> list = communityMapper.selectCommunityList(args);
        //图片地址处理
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).get("picture") != null){
                String picture =
                        minoIOComponent.afterGetContentFromDBToFront(list.get(i).get("picture").toString(),request.getRemoteAddr());
                list.get(i).put("picture",picture);
            }else {
                list.get(i).put("picture","");
            }
        }

        result.put("communitys",list); //数据
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
        CommunityMenu communityMenu = communityMenuMapper.selectById(menuId);
        result.add(menuId);
        if(communityMenu.getTier().equals(3)){
            return result;
            //2级菜单
        }else if(communityMenu.getTier().equals(2)){
            QueryWrapper<CommunityMenu> secondQuery = new QueryWrapper<>();
            secondQuery.eq("tier",3).eq("father",menuId);
            List<CommunityMenu> wikiMenus = communityMenuMapper.selectList(secondQuery);
            //把所有的 子菜单得id 放进去
            for (int i = 0; i < wikiMenus.size(); i++) {
                result.add(wikiMenus.get(i).getId());
            }
            //1级菜单
        }else {
            ArrayList<Integer> list = communityMenuMapper.selectUnderFirstMenu(menuId);
            //把所有子菜单得id 放进去
            for (int i = 0; i < list.size(); i++) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    /**
     * @Author nanguangjun
     * @Description // select community list by member
     * @Date 14:22 2021/3/18
     * @Param [params]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @Override
    public Map<String,Object> selectCommunityListByMember(Map<String, Integer> params){
        Map<String, Object> result = new HashMap<>(); //最后返回值
        int memberId = params.get("memberId");
        int size = params.get("itemsPerPage");
        int page = params.get("page");
        Map<String,Object> totalMapArgs = new HashMap<>();
        totalMapArgs.put("memberId",memberId); //get menu id list
        Integer total = communityMapper.selectCommunityListCount(totalMapArgs); //select total
        int pages = total%size==0 ? total/size : total/size+1;

        //分页查询传参
        Map<String,Object> args = new HashMap<>();
        args.put("memberId",memberId);
        args.put("page",(page-1)*size);
        args.put("size",size);
        List<Map<String, Object>> list = communityMapper.selectCommunityList(args);

        result.put("communitys",list); //数据
        result.put("page",page); //当前页面
        result.put("pages",pages); //总页数
        return result;
    }


    /**
     * @Author nanguangjun
     * @Description // community detail
     * @Date 10:06 2021/4/9
     * @Param [id, request]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @Override
    public Map<String, Object> selectCommunityDetail(Integer id, HttpServletRequest request) {
        Map<String, Object> map = communityMapper.selectCommunityDetail(id);
        String picture = "";
        if(map.get("picture") != null){
            picture = minoIOComponent.afterGetContentFromDBToFront(map.get("picture").toString(),request.getRemoteAddr());
        }
        String content = minoIOComponent.afterGetContentFromDBToFront(map.get("content").toString(), request.getRemoteAddr());
        map.put("content",content);
        map.put("picture",picture);
        return map;
    }

    /**
     * @Author nanguangjun
     * @Description //select community by main page
     * @Date 9:42 2021/3/26
     * @Param []
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> selectCommunityInMainPage(Map<String,Integer> param,HttpServletRequest request) {
        List<Map<String, Object>> list = communityMapper.selectCommunityInMainPage(param);
        //图片地址处理
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).get("picture") != null){
                String picture =
                        minoIOComponent.afterGetContentFromDBToFront(list.get(i).get("picture").toString(),request.getRemoteAddr());
                list.get(i).put("picture",picture);
            }else {
                list.get(i).put("picture","");
            }
        }
        return list;
    }

    /**
     * @Author nanguangjun
     * @Description // get community MenuId by id
     * @Date 10:45 2021/6/2
     * @Param [id]
     * @return java.lang.Integer
     **/
    @Override
    public Integer getCommunityMenuId(Integer id) {
        return communityMapper.selectById(id).getMenuId();
    }
}
