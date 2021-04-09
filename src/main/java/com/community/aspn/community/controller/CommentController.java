package com.community.aspn.community.controller;

import com.community.aspn.community.service.CommentService;
import com.community.aspn.pojo.community.ComComment;
import com.community.aspn.util.AjaxResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    CommentService commentService;

    /**
     * @Author nanguangjun
     * @Description //save comment
     * @Date 15:02 2021/3/23
     * @Param [comComment]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/comments")
    public @ResponseBody AjaxResponse insertComment(@RequestBody ComComment comComment){
        int i = commentService.insertComment(comComment);
        return AjaxResponse.success(i);
    }

    /**
     * @Author nanguangjun
     * @Description //select comment by community id
     * @Date 15:02 2021/3/23
     * @Param [communityId]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/comments/{communityId}")
    public @ResponseBody AjaxResponse getCommentsByCommunityId(@PathVariable Integer communityId,HttpServletRequest request){
        //comment list select
        List<Map<String, Object>> list = commentService.selectCommentByCommunityId(communityId,request);
        //comment count
        Integer integer = commentService.selectCommentCountByCommunityId(communityId);
        Map<String, Object> map = new HashMap<>();
        map.put("list",list);
        map.put("count",integer);
        return AjaxResponse.success(map);
    }

    /**
     * @Author nanguangjun
     * @Description // select comment page list by member id
     * @Date 15:24 2021/3/23
     * @Param [request]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/comments/pageList")
    public @ResponseBody AjaxResponse getCommentPageListByMemberId(HttpServletRequest request){
        Map<String, Integer> map = new HashMap<>();
        map.put("memberId", Integer.valueOf(request.getParameter("memberId")));
        map.put("page", Integer.valueOf(request.getParameter("page")));
        map.put("itemsPerPage",Integer.parseInt(request.getParameter("itemsPerPage")));
        Map<String, Object> stringObjectMap = commentService.selectCommentPageListByMemberId(map);
        return AjaxResponse.success(stringObjectMap);
    }

    @DeleteMapping("/comments/{id}")
    public @ResponseBody AjaxResponse deleteCommentById(@PathVariable Integer id){
        commentService.deleteCommentById(id);
        return AjaxResponse.success();
    }



}
