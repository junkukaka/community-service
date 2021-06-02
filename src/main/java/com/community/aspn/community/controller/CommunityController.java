package com.community.aspn.community.controller;

import com.community.aspn.community.service.CommunityService;
import com.community.aspn.pojo.community.Community;
import com.community.aspn.util.AjaxResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author nanguangjun
 * @Description // 论坛内容
 * @Date 10:47 2021/1/26
 * @Param
 * @return
 **/
@RestController
@RequestMapping("/community")
//@CrossOrigin(origins="*",maxAge=3600)
public class CommunityController {

    @Resource
    CommunityService communityService;

    @PostMapping("/communitys")
    public @ResponseBody  AjaxResponse saveCommunity(@RequestBody Community community){
        int code = 0;
        if(community.getId() != null){
            code = communityService.updateCommunity(community);
        }else {
            code = communityService.insertCommunity(community);
        }
        return AjaxResponse.success(code);
    }
    
    /**
     * @Author nanguangjun
     * @Description // detail 查询
     * @Date 16:40 2021/1/27
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/communitys/{id}")
    public @ResponseBody AjaxResponse selectCommunityById(@PathVariable Integer id,HttpServletRequest request){
        Map<String, Object> stringObjectMap = communityService.selectCommunityDetail(id,request);
        return AjaxResponse.success(stringObjectMap);
    }

    /**
     * @Author nanguangjun
     * @Description // select list by member
     * @Date 14:12 2021/3/18
     * @Param [map]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/communitys/getListByMember")
    public @ResponseBody AjaxResponse selectCommunityListByMember(HttpServletRequest request){
        Map<String, Integer> map = new HashMap<>();
        map.put("memberId",Integer.parseInt(request.getParameter("memberId")));
        map.put("itemsPerPage",Integer.parseInt(request.getParameter("itemsPerPage")));
        map.put("page",Integer.parseInt(request.getParameter("page")));
        Map<String, Object> communities = communityService.selectCommunityListByMember(map);
        return AjaxResponse.success(communities);
    }

    /**
     * @Author nanguangjun
     * @Description // delete community by Id
     * @Date 14:12 2021/3/18
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @DeleteMapping("/communitys/{id}")
    public @ResponseBody AjaxResponse deleteCommunityById(@PathVariable Integer id){
        int i = communityService.deleteCommunityById(id);
        return AjaxResponse.success(i);
    }

    /**
     * @Author nanguangjun
     * @Description // community list by menuId
     * @Date 14:11 2021/3/18
     * @Param [request]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/communitys/pageList")
    public @ResponseBody AjaxResponse selectPageList(HttpServletRequest request){
        Map<String, Integer> map = new HashMap<>();
        map.put("menuId", Integer.valueOf(request.getParameter("menuId")));
        map.put("page", Integer.valueOf(request.getParameter("page")));
        map.put("itemsPerPage",Integer.parseInt(request.getParameter("itemsPerPage")));
        Map<String, Object> stringObjectMap = communityService.selectPageList(map,request);
        return AjaxResponse.success(stringObjectMap);
    }

    /**
     * @Author nanguangjun
     * @Description //main page community select
     * @Date 9:52 2021/3/26
     * @Param [request]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/communitys/mainPage")
    public @ResponseBody AjaxResponse selectCommunityInMainPage(@RequestBody Map<String,Integer> param, HttpServletRequest request){
        List<Map<String, Object>> maps = communityService.selectCommunityInMainPage(param,request);
        return AjaxResponse.success(maps);
    }

    /**
     * @Author nanguangjun
     * @Description // get community menu Id by Id
     * @Date 10:45 2021/6/2
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/getCommunityMenuId/{id}")
    public @ResponseBody AjaxResponse getCommunityMenuId(@PathVariable Integer id){
        Integer menuId = communityService.getCommunityMenuId(id);
        return AjaxResponse.success(menuId);
    }
}
