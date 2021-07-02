package com.community.aspn.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.aspn.pojo.community.ComComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentMapper extends BaseMapper<ComComment> {
    List<Map<String,Object>> selectCommentByCommunityId(Integer communityId);
    Integer selectCommentCountByCommunityId(Integer communityId);
    List<Map<String,Object>> selectCommentByMemberId(Map<String,Integer> args);
    Integer selectCommentCountByMemberId(Integer memberId);
    List<Map<String,Object>> getMyCommunityComment(Integer memberId);
    Integer getMyCommunityCommentCount(Integer memberId);
    void updateCommentReadByCommunityId(Integer communityId);
}
