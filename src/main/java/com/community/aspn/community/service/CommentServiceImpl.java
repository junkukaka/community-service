package com.community.aspn.community.service;

import com.community.aspn.community.mapper.CommentMapper;
import com.community.aspn.pojo.ComComment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService{

    @Resource
    CommentMapper commentMapper;

    @Override
    public int insertComment(ComComment comComment) {
        comComment.setRegisterTime(new Date());
        comComment.setRegisterId(comComment.getUserId());
        return commentMapper.insert(comComment);
    }

    @Override
    public int updateCommentById(ComComment comComment) {
        return 0;
    }

    @Override
    public int deleteCommentById(ComComment comComment) {
        return 0;
    }

    @Override
    public ComComment selectById(Integer id) {
        return null;
    }

    @Override
    public List<Map<String, Object>> selectCommentByCommunityId(Integer communityId) {
        return null;
    }
}
