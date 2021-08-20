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

    @PostMapping("/communities")
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
    @GetMapping("/communities/{id}")
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
    @GetMapping("/communities/getListByMember")
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
    @DeleteMapping("/communities/{id}")
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
    @GetMapping("/communities/pageList")
    public @ResponseBody AjaxResponse selectPageList(HttpServletRequest request){
        Map<String, Integer> map = new HashMap<>();
        map.put("menuId", Integer.valueOf(request.getParameter("menuId")));
        map.put("page", Integer.valueOf(request.getParameter("page")));
        map.put("itemsPerPage",Integer.parseInt(request.getParameter("itemsPerPage")));
        map.put("authority",Integer.parseInt(request.getParameter("authority")));
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
    @GetMapping("/communities/mainPage")
    public @ResponseBody AjaxResponse selectCommunityInMainPage(HttpServletRequest request){
        Integer count = Integer.parseInt(request.getParameter("count"));
        Integer authority = Integer.parseInt(request.getParameter("authority"));
        Map<String,Object> params = new HashMap<>();
        params.put("count",count);
        params.put("authority",authority);
        params.put("remoteAddr",request.getRemoteAddr());
        List<Map<String, Object>> maps = communityService.selectCommunityInMainPage(params);
        return AjaxResponse.success(maps);
    }

    /**
     * @Author nanguangjun
     * @Description //community main template 커뮤니티 메인 페이지 Virtual scroller
     * @Date 16:22 2021/8/18
     * @Param [request]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/communities/selectCommunityTemplatePage")
    public @ResponseBody AjaxResponse selectCommunityTemplatePage(HttpServletRequest request){
        Integer size = Integer.parseInt(request.getParameter("size"));
        Integer page = Integer.parseInt(request.getParameter("page"));
        Integer authority = Integer.parseInt(request.getParameter("authority"));
        Map<String,Object> params = new HashMap<>();
        params.put("size",size);
        params.put("page",page);
        params.put("authority",authority);
        params.put("remoteAddr",request.getRemoteAddr());
        List<Map<String, Object>> maps = communityService.selectCommunityTemplatePage(params);
        return AjaxResponse.success(maps);
    }

    /**
     * @Author nanguangjun
     * @Description //community main template 커뮤니티 메인 페이지 Virtual scroller count
     * @Date 14:10 2021/8/19
     * @Param [authority]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/communities/selectCommunityTemplatePageCount/{authority}")
    public @ResponseBody AjaxResponse selectCommunityTemplatePageCount(@PathVariable Integer authority){
        Integer cnt = communityService.selectCommunityTemplatePageCount(authority);
        return AjaxResponse.success(cnt);
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
