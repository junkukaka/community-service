package com.community.aspn.comInfo.controller;


import com.community.aspn.comInfo.service.ComInfoService;
import com.community.aspn.pojo.community.ComCollect;
import com.community.aspn.pojo.community.ComHits;
import com.community.aspn.pojo.community.ComLikes;
import com.community.aspn.util.AjaxResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author nanguangjun
 * @Description //关于community的 点击量，收藏，喜欢
 * @Date 14:06 2021/3/9
 * @Param
 * @return
 **/
@RestController
@RequestMapping("/comInfo")
public class ComInfoController {

    @Resource
    ComInfoService comInfoService;

    /**
     * @Author nanguangjun
     * @Description //community likes , collect , hits
     * @Date 15:42 2021/3/9
     * @Param [communityId]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/count/{communityId}")
    public @ResponseBody AjaxResponse getComInfoCount(@PathVariable Integer communityId){
        Map<String, Integer> result = comInfoService.selectComInfoCountByCommunityId(communityId);
        return AjaxResponse.success(result);
    }

    /**
     * @Author nanguangjun
     * @Description // counting hits
     * @Date 15:50 2021/3/9
     * @Param [comHits]
     * @return void
     **/
    @PostMapping("/save/hits")
    public void saveHits(@RequestBody ComHits comHits, HttpServletRequest req){
        //设置ip
        comHits.setIp(req.getRemoteAddr());
        comInfoService.saveHits(comHits);
    }

    /**
     * @Author nanguangjun
     * @Description //save likes
     * @Date 15:53 2021/3/9
     * @Param [comLikes]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/save/likes")
    public @ResponseBody AjaxResponse saveLies(@RequestBody ComLikes comLikes){
        return AjaxResponse.success(comInfoService.saveLikes(comLikes));
    }

    /**
     * @Author nanguangjun
     * @Description //save collect
     * @Date 15:55 2021/3/9
     * @Param [comCollect]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/save/collect")
    public @ResponseBody AjaxResponse saveCollect(@RequestBody ComCollect comCollect){
        return AjaxResponse.success(comInfoService.saveCollete(comCollect));
    }



}
