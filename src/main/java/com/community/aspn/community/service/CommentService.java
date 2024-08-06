package com.community.aspn.community.service;

import com.community.aspn.pojo.community.ComComment;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface CommentService {
    int insertComment(ComComment comComment);
    void deleteCommentById(Integer comComment);
    List<Map<String,Object>> selectCommentByCommunityId(Integer communityId, HttpServletRequest request);
    Integer selectCommentCountByCommunityId(Integer communityId);
    Map<String, Object> selectCommentPageListByMemberId(Map<String,Integer> params);
    List<Map<String,Object>> getMyCommunityComment(Integer memberId);
    Integer getMyCommunityCommentCount(Integer memberId);
    void readComment(Integer id);
    void updateComment(ComComment comComment);
}
