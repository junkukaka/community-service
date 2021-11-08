package com.community.aspn.wikiInfo.controller;

import com.community.aspn.pojo.wiki.WikiCollect;
import com.community.aspn.pojo.wiki.WikiHits;
import com.community.aspn.pojo.wiki.WikiLike;
import com.community.aspn.util.AjaxResponse;
import com.community.aspn.wikiInfo.service.WikiInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/wikiInfo")
public class WikiInfoController {

    @Resource
    WikiInfoService wikiInfoService;

    /**
     * @Author nanguangjun
     * @Description //community likes , collect , hits
     * @Date 15:42 2021/3/9
     * @Param [communityId]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/count/{wikiId}")
    public @ResponseBody AjaxResponse getWikiInfoCount(@PathVariable Integer wikiId){
        Map<String, Integer> result = wikiInfoService.selectWikiInfoCountByCommunityId(wikiId);
        return AjaxResponse.success(result);
    }

    /**
     * @Author nanguangjun
     * @Description //use community id and member id select whether member likes or collected this community
     * @Date 14:40 2021/3/15
     * @Param [map]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/selectLikeAndCollectByMember")
    public @ResponseBody AjaxResponse selectLikeAndCollectByMember(@RequestBody Map<String,Integer> map){
        Map<String, Integer> resultMap = wikiInfoService.selectLikeAndCollectByMember(map);
        return AjaxResponse.success(resultMap);
    }

    /**
     * @Author nanguangjun
     * @Description // counting hits
     * @Date 15:50 2021/3/9
     * @Param [comHits]
     * @return void
     **/
    @PostMapping("/save/hits")
    public void saveHits(@RequestBody WikiHits wikiHits, HttpServletRequest req){
        //设置ip
        wikiHits.setIp(req.getRemoteAddr());
        wikiInfoService.saveHits(wikiHits);
    }

    /**
     * @Author nanguangjun
     * @Description //save likes
     * @Date 15:53 2021/3/9
     * @Param [comLikes]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/save/likes")
    public @ResponseBody AjaxResponse saveLies(@RequestBody WikiLike wikiLike){
        return AjaxResponse.success(wikiInfoService.saveLikes(wikiLike));
    }

    /**
     * @Author nanguangjun
     * @Description //save collect
     * @Date 15:55 2021/3/9
     * @Param [comCollect]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/save/collect")
    public @ResponseBody AjaxResponse saveCollect(@RequestBody WikiCollect wikiCollect){
        return AjaxResponse.success(wikiInfoService.saveCollete(wikiCollect));
    }

    /**
     * @Author nanguangjun
     * @Description //Profile page select likes
     * @Date 13:57 2021/3/25
     * @Param [request]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/likes/likesPageList")
    public @ResponseBody AjaxResponse selectLikesPageList(HttpServletRequest request){
        Map<String, Integer> map = new HashMap<>();
        map.put("memberId", Integer.valueOf(request.getParameter("memberId")));
        map.put("page", Integer.valueOf(request.getParameter("page")));
        map.put("itemsPerPage",Integer.parseInt(request.getParameter("itemsPerPage")));
        Map<String, Object> stringObjectMap = wikiInfoService.selectLikesPageListByMemberId(map);
        return AjaxResponse.success(stringObjectMap);
    }

    /**
     * @Author nanguangjun
     * @Description //Profile page select collect
     * @Date 13:57 2021/3/25
     * @Param [request]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/collect/collectPageList")
    public @ResponseBody AjaxResponse selectCollectPageList(HttpServletRequest request){
        Map<String, Integer> map = new HashMap<>();
        map.put("memberId", Integer.valueOf(request.getParameter("memberId")));
        map.put("page", Integer.valueOf(request.getParameter("page")));
        map.put("itemsPerPage",Integer.parseInt(request.getParameter("itemsPerPage")));
        Map<String, Object> stringObjectMap = wikiInfoService.selectCollectPageListByMemberId(map);
        return AjaxResponse.success(stringObjectMap);
    }



}
