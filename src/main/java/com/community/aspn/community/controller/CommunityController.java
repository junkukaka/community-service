package com.community.aspn.community.controller;

import com.community.aspn.community.service.CommunityService;
import com.community.aspn.pojo.Community;
import com.community.aspn.util.AjaxResponse;
import org.springframework.http.HttpRequest;
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
@CrossOrigin(origins="*",maxAge=3600)
public class CommunityController {

    @Resource
    CommunityService communityService;

    @PostMapping("/communitys")
    public @ResponseBody  AjaxResponse insertCommunity(@RequestBody Community community){
        int i = communityService.insertCommunity(community);
        return AjaxResponse.success(i);
    }
    
    /**
     * @Author nanguangjun
     * @Description // detail 查询
     * @Date 16:40 2021/1/27
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/communitys/{id}")
    public @ResponseBody AjaxResponse selectCommunityById(@PathVariable Integer id){
        Community community = communityService.selectCommunityById(id);
        return AjaxResponse.success(community);
    }

    @PostMapping("/communitys/getList")
    public @ResponseBody AjaxResponse selectCommunityList(@RequestBody Map<String,Object> map){
        List<Community> communities = communityService.selectAll(map);
        return AjaxResponse.success(communities);
    }

    @DeleteMapping("/communitys/{id}")
    public @ResponseBody AjaxResponse deleteCommunityById(@PathVariable Integer id){
        int i = communityService.deleteCommunityById(id);
        return AjaxResponse.success(i);
    }

    @GetMapping("/communitys/pageList")
    public @ResponseBody AjaxResponse selectPageList(HttpServletRequest request){
        Map<String, Integer> map = new HashMap<>();
        map.put("menuId", Integer.valueOf(request.getParameter("menuId")));
        map.put("page", Integer.valueOf(request.getParameter("page")));
        Map<String, Object> stringObjectMap = communityService.selectPageList(map);
        return AjaxResponse.success(stringObjectMap);
    }
}
