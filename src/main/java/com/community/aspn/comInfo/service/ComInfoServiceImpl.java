package com.community.aspn.comInfo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.community.aspn.comInfo.mapper.ComCollectMapper;
import com.community.aspn.comInfo.mapper.ComHitsMapper;
import com.community.aspn.comInfo.mapper.ComLikesMapper;
import com.community.aspn.community.mapper.CommentMapper;
import com.community.aspn.pojo.community.ComCollect;
import com.community.aspn.pojo.community.ComComment;
import com.community.aspn.pojo.community.ComHits;
import com.community.aspn.pojo.community.ComLikes;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ComInfoServiceImpl implements ComInfoService {

    @Resource
    ComCollectMapper comCollectMapper;

    @Resource
    ComHitsMapper comHitsMapper;

    @Resource
    ComLikesMapper comLikesMapper;

    @Resource
    CommentMapper commentMapper;

    /**
     * @Author nanguangjun
     * @Description // 点击量统计
     * @Date 14:15 2021/3/9
     * @Param [comHits]
     * @return void
     **/
    @Override
    public void saveHits(ComHits comHits) {
        QueryWrapper<ComHits> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("community_id",comHits.getCommunityId())
                .eq("user_id",comHits.getUserId());
        //if there is no user id and community id will counting hits
        Integer count = comHitsMapper.selectCount(queryWrapper);
        if(count == 0){
            comHits.setRegisterTime(new Date());
            comHitsMapper.insert(comHits);
        }else {
            comHits.setUpdateTime(new Date());
            comHitsMapper.updateById(comHits);
        }
    }

    /**
     * @Author nanguangjun
     * @Description // save uer collect community
     * @Date 14:18 2021/3/9
     * @Param [comCollect]
     * @return int
     **/
    @Override
    public int saveCollete(ComCollect comCollect) {
        QueryWrapper<ComCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("community_id",comCollect.getCommunityId())
                .eq("user_id",comCollect.getUserId());
        Integer count = comCollectMapper.selectCount(queryWrapper);
        //if there is no user id and community id will save hits
        if (count == 0){
            comCollect.setRegisterTime(new Date());
            comCollectMapper.insert(comCollect);
        }else {
            comCollectMapper.delete(queryWrapper);
            count = 0;
        }
        return count;
    }

    /**
     * @Author nanguangjun
     * @Description // save likes community
     * @Date 15:23 2021/3/9
     * @Param [comLikes]
     * @return int
     **/
    @Override
    public int saveLikes(ComLikes comLikes) {
        comLikes.setLikeYn(1);
        QueryWrapper<ComLikes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("community_id", comLikes.getCommunityId());
        queryWrapper.eq("user_id", comLikes.getUserId());
        Integer count = comLikesMapper.selectCount(queryWrapper);
        //if there is no user id and community id will save hits
        if (count == 0){
            comLikes.setRegisterTime(new Date());
            comLikesMapper.insert(comLikes);
        }else {
            comLikesMapper.delete(queryWrapper);
            count = 0;
        }
        return count;
    }

    /**
     * @Author nanguangjun
     * @Description // select hits ,likes , collect
     * @Date 15:26 2021/3/9
     * @Param [communityId]
     * @return java.util.Map<java.lang.String,java.lang.Integer>
     **/
    @Override
    public Map<String, Integer> selectComInfoCountByCommunityId(Integer communityId) {
        Map<String, Integer> result = new HashMap<>(); //最终返回的结果
        //hits count
        QueryWrapper<ComHits> hitsQueryWrapper = new QueryWrapper<>();
        hitsQueryWrapper.eq("community_id",communityId);
        result.put("hitsCount",comHitsMapper.selectCount(hitsQueryWrapper));
        //likes count
        QueryWrapper<ComLikes> likesQueryWrapper = new QueryWrapper<>();
        likesQueryWrapper.eq("community_id",communityId);
        result.put("likesCount",comLikesMapper.selectCount(likesQueryWrapper));
        //collect count
        QueryWrapper<ComCollect> collectQueryWrapper = new QueryWrapper<>();
        collectQueryWrapper.eq("community_id",communityId);
        result.put("collectCount",comCollectMapper.selectCount(collectQueryWrapper));
        //comment count
        QueryWrapper<ComComment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("community_id",communityId);
        result.put("commentCount",commentMapper.selectCount(commentQueryWrapper));
        return result;
    }

}
