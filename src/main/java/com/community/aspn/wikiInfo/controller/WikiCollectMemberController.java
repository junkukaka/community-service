package com.community.aspn.wikiInfo.controller;


import com.community.aspn.pojo.wiki.WikiCollect;
import com.community.aspn.pojo.wiki.WikiMemberCollectMenu;
import com.community.aspn.util.AjaxResponse;
import com.community.aspn.wikiInfo.service.WikiCollectMemberService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author nanguangjun
 * @Description // 위키 collect
 * @Date 12:58 2021/11/16
 * @Param
 * @return
 **/
@RestController
@RequestMapping("/wikiCollect")
public class WikiCollectMemberController {

    @Resource
    WikiCollectMemberService wikiCollectMemberService;

    /**
     * @Author nanguangjun
     * @Description // 위키메뉴 저장
     * @Date 13:51 2021/11/17
     * @Param [wikiMemberCollectMenu]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/collectMenu/saveCollectWikiMemberCollectMenu")
    public @ResponseBody AjaxResponse saveCollectWikiMemberCollectMenu(@RequestBody WikiMemberCollectMenu wikiMemberCollectMenu){
        try {
            wikiCollectMemberService.saveCollectWikiMemberCollectMenu(wikiMemberCollectMenu);
            return AjaxResponse.success();
        }catch (Exception e){
            return AjaxResponse.error();
        }
    }

    /**
     * @Author nanguangjun
     * @Description // 위키 메뉴 삭제
     * @Date 13:51 2021/11/17
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @DeleteMapping("/collectMenu/deleteCollectWikiMemberCollectMenu/{id}")
    public @ResponseBody AjaxResponse updateCollectWikiMemberCollectMenu(@PathVariable Integer id ){
        try {
            wikiCollectMemberService.deleteCollectWikiMemberCollectMenu(id);
            return AjaxResponse.success();
        }catch (Exception e){
            return AjaxResponse.error();
        }
    }

    /**
     * @Author nanguangjun
     * @Description // 위키 메뉴 수정
     * @Date 13:51 2021/11/17
     * @Param [wikiMemberCollectMenu]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PutMapping("/collectMenu/updateCollectWikiMemberCollectMenu")
    public @ResponseBody AjaxResponse updateCollectWikiMemberCollectMenu(@RequestBody WikiMemberCollectMenu wikiMemberCollectMenu){
        try{
            wikiCollectMemberService.updateCollectWikiMemberCollectMenu(wikiMemberCollectMenu);
            return AjaxResponse.success();
        }catch (Exception e){
            return AjaxResponse.error();
        }
    }

    /**
     * @Author nanguangjun
     * @Description // 위키 메뉴 조회
     * @Date 13:51 2021/11/17
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/collectMenu/selectCollectMenuListByMember/{id}")
    public @ResponseBody AjaxResponse selectCollectMenuListByMember(@PathVariable Integer id){
        try {
            List<WikiMemberCollectMenu> wikiMemberCollectMenus = wikiCollectMemberService.selectCollectWikiMemberCollectMenu(id);
            return AjaxResponse.success(wikiMemberCollectMenus);
        }catch (Exception e){
            return AjaxResponse.error();
        }
    }


    /**
     * @Author nanguangjun
     * @Description // 위키 즐겨찾기 정장
     * @Date 13:53 2021/11/17
     * @Param [wikiCollect]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/collect/saveWikiCollect")
    public @ResponseBody AjaxResponse saveWikiCollect(@RequestBody WikiCollect wikiCollect){
        try {
            wikiCollectMemberService.saveWikiCollect(wikiCollect);
            return AjaxResponse.success();
        }catch (Exception e){
            return AjaxResponse.error();
        }
    }

    /**
     * @Author nanguangjun
     * @Description //collect 메뉴별고 위키 조회하기
     * @Date 9:52 2021/11/19
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/collect/selectWikiCollect/{id}")
    public @ResponseBody AjaxResponse selectWikiCollect(@PathVariable Integer id){
        try {
            List<Map<String, Object>> result = wikiCollectMemberService.selectWikiCollect(id);
            return AjaxResponse.success(result);
        }catch (Exception e){
            return AjaxResponse.error();
        }
    }


    /**
     * @Author nanguangjun
     * @Description // 전체 회원별 즐겨찾기
     * @Date 9:50 2021/11/19
     * @Param [memberId]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/collect/selectAllWikiCollect/{memberId}")
    public @ResponseBody AjaxResponse selectAllWikiCollect(@PathVariable Integer memberId){
        try {
            List<Map<String, Object>> result = wikiCollectMemberService.selectAllWikiCollect(memberId);
            return AjaxResponse.success(result);
        }catch (Exception e){
            return AjaxResponse.error();
        }
    }

    /**
     * @Author nanguangjun
     * @Description // 즐겨찾기 삭제하기
     * @Date 15:59 2021/11/18
     * @Param [wikiId, memberId]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @DeleteMapping("/collect/deleteWikiCollect/{wikiId}/{memberId}")
    public @ResponseBody AjaxResponse deleteWikiCollect(@PathVariable Integer wikiId,@PathVariable Integer memberId){
        HashMap<String, Integer> params = new HashMap<>();
        params.put("wikiId",wikiId);
        params.put("memberId",memberId);
        try {
            wikiCollectMemberService.deleteWikiCollect(params);
            return AjaxResponse.success();
        }catch (Exception e){
            return AjaxResponse.error();
        }
    }


}
