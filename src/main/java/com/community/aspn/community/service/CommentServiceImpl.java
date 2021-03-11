package com.community.aspn.community.service;

import com.community.aspn.community.mapper.CommentMapper;
import com.community.aspn.pojo.community.ComComment;
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
        comComment.setRegisterId(comComment.getMemberId());
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
    
    /**
     * @Author nanguangjun
     * @Description //댓글 조회 
     * @Date 16:29 2021/3/2
     * @Param [communityId]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> selectCommentByCommunityId(Integer communityId) {
        List<Map<String, Object>> list = commentMapper.selectCommentByCommunityId(communityId);
        return list;
    }

    /**
     * @Author nanguangjun
     * @Description // 根据 community id 获取 comment数量
     * @Date 10:37 2021/3/3
     * @Param [communityId]
     * @return java.lang.Integer
     **/
    @Override
    public Integer selectCommentCountByCommunityId(Integer communityId) {
        Integer integer = commentMapper.selectCommentCountByCommunityId(communityId);
        return integer;
    }
}
