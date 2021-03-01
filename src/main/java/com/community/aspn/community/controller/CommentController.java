package com.community.aspn.community.controller;

import com.community.aspn.community.service.CommentService;
import com.community.aspn.pojo.ComComment;
import com.community.aspn.util.AjaxResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
}
