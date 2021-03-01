package com.community.aspn.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.aspn.pojo.ComComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentMapper extends BaseMapper<ComComment> {
    List<Map<String,Object>> selectCommentByCommunityId(Integer communityId);
}
