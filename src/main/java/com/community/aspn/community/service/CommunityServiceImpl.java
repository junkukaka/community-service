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
     * @Date 16:44 2021/1/27
     * @Param [map]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @Override
    public Map<String, Object> selectPageList(Map<String, Integer> params) {
        Map<String, Object> result = new HashMap<>(); //最后返回值
        QueryWrapper<Community> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_id",params.get("menuId"));
        Menu menu = menuMapper.selectById(params.get("menuId"));

        Page<Community> page = new Page<>(params.get("page"),20);  //查询第1页，每页5条数据
        communityMapper.selectPage(page, queryWrapper);
        //分页返回的对象与传入的对象是同一个
        List<Community> communitys = page.getRecords();
        result.put("menuName",menu.getName());
        result.put("communitys",communitys); //数据
        result.put("page",params.get("page")); //当前页面
        result.put("pages",page.getPages()); //总页数
        result.put("total",page.getTotal()); //总记录数
        return result;
    }

    @Override
    public Map<String, Object> selectCommunityDetail(Integer id) {
        return null;
    }
}
