package com.community.aspn.admin.controller;

import com.community.aspn.admin.mapper.ArticleManageMapper;
import com.community.aspn.admin.service.ArticleManageService;
import com.community.aspn.util.AjaxResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/adminArticle")
public class ArticleManageController {

    @Resource
    ArticleManageService articleManageService;
    
    /**
     * @Author nanguangjun
     * @Description // admin 커뮤니티 조회 
     * @Date 15:22 2021/11/26
     * @Param [request]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/community/selectCommunities")
    public @ResponseBody AjaxResponse selectCommunities(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        if(request.getParameter("searchTitle") != null){
            map.put("searchTitle",request.getParameter("searchTitle").toString());
        }
        if(request.getParameter("members") != null){
            map.put("members", Arrays.asList(request.getParameter("members").toString().split(",")));
        }
        map.put("itemsPerPage",Integer.parseInt(request.getParameter("itemsPerPage")));
        map.put("page",Integer.parseInt(request.getParameter("page")));
        Map<String, Object> results = articleManageService.selectCommunities(map);
        return AjaxResponse.success(results);
    }
    
    /**
     * @Author nanguangjun
     * @Description // admin 위키 조회 
     * @Date 15:22 2021/11/26
     * @Param [request]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/wiki/selectWikis")
    public @ResponseBody AjaxResponse selectWikis(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        if(request.getParameter("searchTitle") != null){
            map.put("searchTitle",request.getParameter("searchTitle").toString());
        }
        if(request.getParameter("members") != null){
            map.put("members", Arrays.asList(request.getParameter("members").toString().split(",")));
        }
        map.put("itemsPerPage",Integer.parseInt(request.getParameter("itemsPerPage")));
        map.put("page",Integer.parseInt(request.getParameter("page")));
        Map<String, Object> results = articleManageService.selectWikis(map);
        return AjaxResponse.success(results);
    }
    
    /**
     * @Author nanguangjun
     * @Description // 위키 history 조회
     * @Date 16:11 2021/11/29
     * @Param [wikiId]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/wiki/selectWikiHis/{wikiId}")
    public @ResponseBody AjaxResponse selectWikiHis(@PathVariable Integer wikiId,HttpServletRequest request){
        HashMap<String, Object> params = new HashMap<>();
        params.put("wikiId",wikiId);
        params.put("remoteAddr",request.getRemoteAddr());
        List<Map<String, Object>> list = articleManageService.selectWikiHis(params);
        return AjaxResponse.success(list);
    }

    /**
     * @Author nanguangjun
     * @Description //  위키 history 삭제
     * @Date 14:04 2021/11/30
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @DeleteMapping("/wiki/deleteWikiHis/{id}")
    public @ResponseBody AjaxResponse deleteWikiHis(@PathVariable Integer id){
        articleManageService.deleteWikiHis(id);
        return AjaxResponse.success();
    }

    /**
     * @Author nanguangjun
     * @Description // 위키 삭제
     * @Date 14:52 2021/11/30
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @DeleteMapping("/wiki/deleteWiki/{id}")
    public @ResponseBody AjaxResponse deleteWiki(@PathVariable Integer id){
        articleManageService.deleteWiki(id);
        return AjaxResponse.success();
    }
}
