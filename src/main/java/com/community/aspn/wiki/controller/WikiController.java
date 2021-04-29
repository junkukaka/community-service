package com.community.aspn.wiki.controller;

import com.community.aspn.pojo.wiki.Wiki;
import com.community.aspn.pojo.wiki.WikiHis;
import com.community.aspn.util.AjaxResponse;
import com.community.aspn.util.mino.MinoIOComponent;
import com.community.aspn.wiki.service.WikiService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wiki")
public class WikiController {

    @Resource
    WikiService wikiService;

    @Resource
    MinoIOComponent minoIOComponent;
    
    /**
     * @Author nanguangjun
     * @Description // save wiki
     * @Date 9:01 2021/4/20
     * @Param [params]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/wikiHis")
    public AjaxResponse saveWiki(@RequestBody Map<String,Object> params){
        WikiHis wikiHis = new WikiHis();
        wikiHis.setTitle(params.get("title").toString());
        wikiHis.setMenuId(Integer.parseInt(params.get("menuId").toString()));
        wikiHis.setMemberId(Integer.parseInt(params.get("memberId").toString()));
        wikiHis.setContent(minoIOComponent.beForeFileSaveInDB(params.get("content").toString()));
        wikiHis.setInformation(params.get("information").toString());
        if(params.get("picture") != null){
            String picture = minoIOComponent.beForeFileSaveInDB(params.get("picture").toString());
            wikiHis.setPicture(picture);
        }
        if(params.get("id") != null){
            wikiHis.setId(Integer.parseInt(params.get("id").toString()));
        }
        if(params.get("wikiId") != null){
            wikiHis.setWikiId(Integer.parseInt(params.get("wikiId").toString()));
        }
        //wiki save Y,N
        Boolean flag = Boolean.valueOf(params.get("active").toString());
        return AjaxResponse.success(wikiService.saveWikiHis(wikiHis, flag));
    }

    /**
     * @Author nanguangjun
     * @Description // select wiki list by menuId
     * @Date 16:01 2021/4/20
     * @Param [menuId, request]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/wikis/{menuId}")
    public AjaxResponse selectWikiList(@PathVariable Integer menuId, HttpServletRequest request){
        Wiki wiki = new Wiki();
        wiki.setMenuId(menuId);
        List<Map<String, Object>> maps = wikiService.wikiList(wiki, request.getRemoteAddr());
        return AjaxResponse.success(maps);
    }
    /**
     * @Author nanguangjun
     * @Description // wiki detail page
     * @Date 13:55 2021/4/21
     * @Param [id is wiki id, request]
     * @return com.community.aspn.util.AjaxResponse WikiHis
     **/
    @GetMapping("/wikiDetail/{id}")
    public AjaxResponse selectWikiDetail(@PathVariable Integer id,HttpServletRequest request){
        String remoteAddr = request.getRemoteAddr();
        WikiHis wikiHis = wikiService.selectWikiDetail(id, remoteAddr);
        return AjaxResponse.success(wikiHis);
    }

    /**
     * @Author nanguangjun
     * @Description // wiki Main list
     * @Date 16:20 2021/4/21
     * @Param [request]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/wikiMain/{count}")
    public AjaxResponse selectWikiMain(@PathVariable Integer count, HttpServletRequest request){
        String remoteAddr = request.getRemoteAddr();
        if(count == null){
            AjaxResponse.success(null);
        }
        List<Map<String, Object>> maps = wikiService.wikiMainList(count, remoteAddr);
        return AjaxResponse.success(maps);
    }

    /**
     * @Author nanguangjun
     * @Description //TODO
     * @Date 10:25 2021/4/22
     * @Param [memberId]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/wikiProfileList/{memberId}")
    public AjaxResponse selectWikiHisProfile(@PathVariable Integer memberId){
        List<Map<String, Object>> list = wikiService.selectWikiHisProfile(memberId);
        return AjaxResponse.success(list);
    }

    /**
     * @Author nanguangjun
     * @Description //delete wikiHis by id
     * @Date 15:50 2021/4/22
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @DeleteMapping("/wikiHis/{id}")
    public AjaxResponse deleteWikiHistoryById(@PathVariable Integer id){
        wikiService.deleteWikiHistoryById(id);
        return AjaxResponse.success();
    }

    /**
     * @Author nanguangjun
     * @Description //TODO
     * @Date 15:53 2021/4/22
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/wikiHis/{id}")
    public AjaxResponse selectWikiHisById(@PathVariable Integer id,HttpServletRequest request){
        WikiHis wikiHis = wikiService.selectWikiHisByID(id,request.getRemoteAddr());
        return AjaxResponse.success(wikiHis);
    }

    @GetMapping("/wikiHisList/{wikiId}")
    public AjaxResponse selectWikiHisList(@PathVariable Integer wikiId,HttpServletRequest request){
        List<Map<String, Object>> list = wikiService.selectWikiHisList(wikiId, request.getRemoteAddr());
        return AjaxResponse.success(list);
    }

    /**
     * @Author nanguangjun
     * @Description // wiwki back to the history
     * @Date 14:02 2021/4/28
     * @Param [wikiHis]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PutMapping("/backToTheHis")
    public AjaxResponse backToThePastWikiHis(@RequestBody WikiHis wikiHis){
        wikiService.backToThePastWikiHis(wikiHis);
        return AjaxResponse.success();
    }
}
