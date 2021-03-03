package com.community.aspn.community.service;

import com.community.aspn.pojo.community.ComComment;

import java.util.List;
import java.util.Map;

public interface CommentService {
    int insertComment(ComComment comComment);
    int updateCommentById(ComComment comComment);
    int deleteCommentById(ComComment comComment);
    ComComment selectById(Integer id);
    List<Map<String,Object>> selectCommentByCommunityId(Integer communityId);
    Integer selectCommentCountByCommunityId(Integer communityId);
}
