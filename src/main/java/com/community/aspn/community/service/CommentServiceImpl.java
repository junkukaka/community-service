package com.community.aspn.community.service;

import com.community.aspn.community.mapper.CommentMapper;
import com.community.aspn.pojo.community.ComComment;
import com.community.aspn.util.mino.MinoIOComponent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService{

    @Resource
    CommentMapper commentMapper;

    @Resource
    MinoIOComponent minoIOComponent;

    @Override
    public int insertComment(ComComment comComment) {
        comComment.setRegisterTime(new Date());
        comComment.setRegisterId(comComment.getMemberId());
        return commentMapper.insert(comComment);
    }

    /**
     * @Author nanguangjun
     * @Description //delete comment by id
     * @Date 15:28 2021/3/23
     * @Param [id]
     * @return void
     **/
    @Override
    public void deleteCommentById(Integer id) {
        commentMapper.deleteById(id);
    }
    
    /**
     * @Author nanguangjun
     * @Description //댓글 조회 
     * @Date 16:29 2021/3/2
     * @Param [communityId]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> selectCommentByCommunityId(Integer communityId, HttpServletRequest request) {
        List<Map<String, Object>> list = commentMapper.selectCommentByCommunityId(communityId);
        //图片地址处理
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).get("picture") != null){
                String picture =
                        minoIOComponent.afterGetContentFromDBToFront(list.get(i).get("picture").toString(),request.getRemoteAddr());
                list.get(i).put("picture",picture);
            }else {
                list.get(i).put("picture","");
            }
        }
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

    /**
     * @Author nanguangjun
     * @Description Profile page used, select comments list by member id
     * @Date 14:55 2021/3/23
     * @Param [args]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public Map<String, Object> selectCommentPageListByMemberId(Map<String, Integer> params) {
        Map<String, Object> result = new HashMap<>(); //最后返回值
        int memberId = params.get("memberId");
        int size = params.get("itemsPerPage");
        int page = params.get("page");
        Map<String,Integer> totalMapArgs = new HashMap<>();
        totalMapArgs.put("memberId",memberId);
        Integer total = commentMapper.selectCommentCountByMemberId(memberId);
        int pages = total%size==0 ? total/size : total/size+1;

        //分页查询传参
        Map<String,Integer> args = new HashMap<>();
        args.put("memberId",memberId);
        args.put("page",(page-1)*size);
        args.put("size",size);
        List<Map<String, Object>> list = commentMapper.selectCommentByMemberId(args);

        result.put("comments",list); //数据
        result.put("page",page); //当前页面
        result.put("pages",pages); //总页数
        return result;
    }

    /**
     * @Author nanguangjun
     * @Description // community 留言消息提醒List
     * @Date 9:47 2021/6/30
     * @Param [memberId]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> getMyCommunityComment(Integer memberId) {
        List<Map<String, Object>> myCommunityComment = commentMapper.getMyCommunityComment(memberId);
        return myCommunityComment;
    }

    /**
     * @Author nanguangjun
     * @Description //  community 留言消息提醒count
     * @Date 10:05 2021/6/30
     * @Param [memberId]
     * @return java.lang.Integer
     **/
    @Override
    public Integer getMyCommunityCommentCount(Integer memberId) {
        Integer myCommunityCommentCount = commentMapper.getMyCommunityCommentCount(memberId);
        return myCommunityCommentCount;
    }

    /**
     * @Author nanguangjun
     * @Description // read comment by community id
     * @Date 15:53 2021/6/30
     * @Param [id : community id ]
     * @return void
     **/
    @Override
    public void readComment(Integer id) {
        commentMapper.updateCommentReadByCommunityId(id);
    }

    @Override
    public void updateComment(ComComment comComment) {
        commentMapper.updateById(comComment);
    }
}
