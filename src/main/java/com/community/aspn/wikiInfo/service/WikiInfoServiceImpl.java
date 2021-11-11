package com.community.aspn.wikiInfo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.community.aspn.pojo.wiki.WikiCollect;
import com.community.aspn.pojo.wiki.WikiHits;
import com.community.aspn.pojo.wiki.WikiLike;
import com.community.aspn.wikiInfo.mapper.WikiCollectMapper;
import com.community.aspn.wikiInfo.mapper.WikiHitsMapper;
import com.community.aspn.wikiInfo.mapper.WikiLikeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WikiInfoServiceImpl implements WikiInfoService {

    @Resource
    WikiCollectMapper wikiCollectMapper;

    @Resource
    WikiHitsMapper wikiHitsMapper;

    @Resource
    WikiLikeMapper wikiLikeMapper;

    /**
     * @Author nanguangjun
     * @Description //TODO 
     * @Date 15:34 2021/11/2
     * @Param [wikiHits]
     * @return void
     **/
    @Override
    public void saveHits(WikiHits wikiHits) {
        QueryWrapper<WikiHits> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("wiki_id",wikiHits.getWikiId())
                .eq("member_id",wikiHits.getMemberId());
        //if there is no user id and community id will count hits
        Integer count = wikiHitsMapper.selectCount(queryWrapper);
        wikiHits.setRegisterTime(new Date());
        wikiHitsMapper.insert(wikiHits);
    }

    /**
     * @Author nanguangjun
     * @Description //TODO 
     * @Date 15:37 2021/11/2
     * @Param [wikiCollect]
     * @return int
     **/
    @Override
    public int saveCollete(WikiCollect wikiCollect) {
        QueryWrapper<WikiCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("wiki_id",wikiCollect.getWikiId())
                .eq("member_id",wikiCollect.getMemberId());
        Integer count = wikiCollectMapper.selectCount(queryWrapper);
        //if there is no user id and community id will save hits
        if (count == 0){
            wikiCollect.setRegisterTime(new Date());
            wikiCollectMapper.insert(wikiCollect);
        }else {
            wikiCollectMapper.delete(queryWrapper);
            count = 0;
        }
        return count;
    }

    /**
     * @Author nanguangjun
     * @Description //TODO
     * @Date 15:37 2021/11/2
     * @Param [comLikes]
     * @return int
     **/
    @Override
    public int saveLikes(WikiLike wikiLike) {
        wikiLike.setLikeYn(1);
        QueryWrapper<WikiLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("wiki_id", wikiLike.getWikiId());
        queryWrapper.eq("member_id", wikiLike.getMemberId());
        Integer count = wikiLikeMapper.selectCount(queryWrapper);
        //if there is no user id and community id will save hits
        if (count == 0){
            wikiLike.setRegisterTime(new Date());
            wikiLikeMapper.insert(wikiLike);
        }else {
            wikiLikeMapper.delete(queryWrapper);
            count = 0;
        }
        return count;
    }


    /**
     * @Author nanguangjun
     * @Description //TODO
     * @Date 15:40 2021/11/2
     * @Param [wikiId]
     * @return java.util.Map<java.lang.String,java.lang.Integer>
     **/
    @Override
    public Map<String, Integer> selectWikiInfoCountByCommunityId(Integer wikiId) {
        Map<String, Integer> result = new HashMap<>(); //最终返回的结果
        //hits count
        QueryWrapper<WikiHits> hitsQueryWrapper = new QueryWrapper<>();
        hitsQueryWrapper.eq("wiki_id",wikiId);
        hitsQueryWrapper.groupBy("wiki_id");
        Integer hitsCount = wikiHitsMapper.selectCount(hitsQueryWrapper) == null ? 0 : wikiHitsMapper.selectCount(hitsQueryWrapper);
        result.put("hitsCount",hitsCount);
        //likes count
        QueryWrapper<WikiLike> likesQueryWrapper = new QueryWrapper<>();
        likesQueryWrapper.eq("wiki_id",wikiId);
        result.put("likesCount", wikiLikeMapper.selectCount(likesQueryWrapper));
        //collect count
        QueryWrapper<WikiCollect> collectQueryWrapper = new QueryWrapper<>();
        collectQueryWrapper.eq("wiki_id",wikiId);
        result.put("collectCount",wikiCollectMapper.selectCount(collectQueryWrapper));
        return result;
    }

    /**
     * @Author nanguangjun
     * @Description //TODO
     * @Date 15:43 2021/11/2
     * @Param [map]
     * @return java.util.Map<java.lang.String,java.lang.Integer>
     **/
    @Override
    public Map<String, Integer> selectLikeAndCollectByMember(Map<String, Integer> map) {
        QueryWrapper<WikiLike> comLikesQueryWrapper = new QueryWrapper<>();
        comLikesQueryWrapper.eq("wiki_id",map.get("wikiId"))
                .eq("member_id",map.get("memberId"));
        Integer likesCount = wikiLikeMapper.selectCount(comLikesQueryWrapper);

        QueryWrapper<WikiCollect> comCollectQueryWrapper = new QueryWrapper<>();
        comCollectQueryWrapper.eq("wiki_id",map.get("wikiId"))
                .eq("member_id",map.get("memberId"));
        Integer collectCount = wikiCollectMapper.selectCount(comCollectQueryWrapper);

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("memberLikesYn",likesCount);
        resultMap.put("memberCollectYn",collectCount);

        return resultMap;
    }

    /**
     * @Author nanguangjun
     * @Description //TODO
     * @Date 15:43 2021/11/2
     * @Param [params]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @Override
    public Map<String, Object> selectLikesPageListByMemberId(Map<String, Integer> params) {
        Map<String, Object> result = new HashMap<>(); //最后返回值
        int memberId = params.get("memberId");
        int size = params.get("itemsPerPage");
        int page = params.get("page");
        Map<String,Integer> totalMapArgs = new HashMap<>();
        totalMapArgs.put("memberId",memberId);
        Integer total = wikiCollectMapper.selectLikesPageListCountByMemberId(memberId);
        int pages = total%size==0 ? total/size : total/size+1;

        //分页查询传参
        Map<String,Integer> args = new HashMap<>();
        args.put("memberId",memberId);
        args.put("page",(page-1)*size);
        args.put("size",size);
        List<Map<String, Object>> list = wikiCollectMapper.selectLikesPageListByMemberId(args);

        result.put("likes",list); //数据
        result.put("page",page); //当前页面
        result.put("pages",pages); //总页数
        return result;
    }

    /**
     * @Author nanguangjun
     * @Description //TODO
     * @Date 15:43 2021/11/2
     * @Param [params]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @Override
    public Map<String, Object> selectCollectPageListByMemberId(Map<String, Integer> params) {
        Map<String, Object> result = new HashMap<>(); //最后返回值
        int memberId = params.get("memberId");
        int size = params.get("itemsPerPage");
        int page = params.get("page");
        Map<String,Integer> totalMapArgs = new HashMap<>();
        totalMapArgs.put("memberId",memberId);
        Integer total = wikiCollectMapper.selectCollectPageListCountByMemberId(memberId);
        int pages = total%size==0 ? total/size : total/size+1;

        //分页查询传参
        Map<String,Integer> args = new HashMap<>();
        args.put("memberId",memberId);
        args.put("page",(page-1)*size);
        args.put("size",size);
        List<Map<String, Object>> list = wikiCollectMapper.selectCollectPageListByMemberId(args);

        result.put("collect",list); //数据
        result.put("page",page); //当前页面
        result.put("pages",pages); //总页数
        return result;
    }

}
