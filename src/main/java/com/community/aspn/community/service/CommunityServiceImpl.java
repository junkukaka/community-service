package com.community.aspn.community.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.community.aspn.community.mapper.CommunityMapper;
import com.community.aspn.menu.mapper.MenuMapper;
import com.community.aspn.pojo.community.Community;
import com.community.aspn.pojo.sys.Menu;
import com.community.aspn.util.mino.MinIOFileUtil;
import com.community.aspn.util.mino.MinoIOComponent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommunityServiceImpl implements CommunityService{

    @Resource
    CommunityMapper communityMapper;

    @Resource
    MenuMapper menuMapper;

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

        String content = community.getContent();
        //是否有截图，如果有截图，处理截图文件。
        if(MinIOFileUtil.ifBase64RegexMatcher(content)){
            String newContent = minoIOComponent.base64RegexReplace(content);
            community.setContent(newContent);
        }
        return communityMapper.insert(community);
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
        String content = community.getContent();
        //是否有截图，如果有截图，处理截图文件。
        if(MinIOFileUtil.ifBase64RegexMatcher(content)){
            String newContent = minoIOComponent.base64RegexReplace(content);
            community.setContent(newContent);
        }
        return communityMapper.updateById(community);
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
    public Map<String,Object> selectPageList(Map<String, Integer> params){
        Map<String, Object> result = new HashMap<>(); //最后返回值
        int menuId = params.get("menuId");
        int page = params.get("page");
        int size = params.get("itemsPerPage");
        Map<String,Integer> totalMapArgs = new HashMap<>();
        totalMapArgs.put("menuId",menuId);
        Integer total = communityMapper.selectCommunityListCount(totalMapArgs); //select total
        int pages = total%size==0 ? total/size : total/size+1;

        //分页查询传参
        Map<String,Integer> args = new HashMap<>();
        args.put("menuId",menuId);
        args.put("page",(page-1)*size);
        args.put("size",size);
        List<Map<String, Object>> list = communityMapper.selectCommunityList(args);

        Menu menu = menuMapper.selectById(menuId);

        result.put("menuName",menu.getName());
        result.put("communitys",list); //数据
        result.put("page",page); //当前页面
        result.put("pages",pages); //总页数
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
        Map<String,Integer> totalMapArgs = new HashMap<>();
        totalMapArgs.put("memberId",memberId);
        Integer total = communityMapper.selectCommunityListCount(totalMapArgs); //select total
        int pages = total%size==0 ? total/size : total/size+1;

        //分页查询传参
        Map<String,Integer> args = new HashMap<>();
        args.put("memberId",memberId);
        args.put("page",(page-1)*size);
        args.put("size",size);
        List<Map<String, Object>> list = communityMapper.selectCommunityList(args);

        result.put("communitys",list); //数据
        result.put("page",page); //当前页面
        result.put("pages",pages); //总页数
        return result;
    }


    @Override
    public Map<String, Object> selectCommunityDetail(Integer id) {
        return communityMapper.selectCommunityDetail(id);
    }

    /**
     * @Author nanguangjun
     * @Description //select community by main page
     * @Date 9:42 2021/3/26
     * @Param []
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> selectCommunityInMainPage(Map<String,Integer> param) {
        List<Map<String, Object>> list = communityMapper.selectCommunityInMainPage(param);
        return list;
    }
}
