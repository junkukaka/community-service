package com.community.aspn.community.service;

import com.community.aspn.pojo.community.ComComment;

import java.util.List;
import java.util.Map;

public interface CommentService {
    int insertComment(ComComment comComment);
    void deleteCommentById(Integer comComment);
    List<Map<String,Object>> selectCommentByCommunityId(Integer communityId);
    Integer selectCommentCountByCommunityId(Integer communityId);
    Map<String, Object> selectCommentPageListByMemberId(Map<String,Integer> params);
}
