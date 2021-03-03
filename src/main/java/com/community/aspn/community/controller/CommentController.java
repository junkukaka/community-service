package com.community.aspn.community.controller;

import com.community.aspn.community.service.CommentService;
import com.community.aspn.pojo.community.ComComment;
import com.community.aspn.util.AjaxResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    CommentService commentService;

    @PostMapping("/comments")
    public @ResponseBody AjaxResponse insertComment(@RequestBody ComComment comComment){
        int i = commentService.insertComment(comComment);
        return AjaxResponse.success(i);
    }

    @GetMapping("/comments/{communityId}")
    public @ResponseBody AjaxResponse getCommentsByCommunityId(@PathVariable Integer communityId){
        //comment list select
        List<Map<String, Object>> list = commentService.selectCommentByCommunityId(communityId);
        //comment count
        Integer integer = commentService.selectCommentCountByCommunityId(communityId);
        Map<String, Object> map = new HashMap<>();
        map.put("list",list);
        map.put("count",integer);
        return AjaxResponse.success(map);
    }


}
