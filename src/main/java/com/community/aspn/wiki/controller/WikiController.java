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
        List<Map<String, Object>> maps = wikiService.wikiList(wiki, request);
        return AjaxResponse.success(maps);
    }

}
