package com.community.aspn.wikiInfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.aspn.pojo.wiki.WikiCollect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface WikiCollectMapper extends BaseMapper<WikiCollect> {
   List<Map<String,Object>> selectLikesPageListByMemberId(Map<String,Integer> args);
   Integer selectLikesPageListCountByMemberId(Integer memberId);

   List<Map<String,Object>> selectWikiCollect(Integer menuId);
   List<Map<String,Object>> selectAllWikiCollect(Integer memberId);

}
