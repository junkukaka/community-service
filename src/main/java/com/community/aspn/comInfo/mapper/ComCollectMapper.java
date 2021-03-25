package com.community.aspn.comInfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.aspn.pojo.community.ComCollect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ComCollectMapper extends BaseMapper<ComCollect> {
   List<Map<String,Object>> selectLikesPageListByMemberId(Map<String,Integer> args);
   Integer selectLikesPageListCountByMemberId(Integer memberId);

   List<Map<String,Object>> selectCollectPageListByMemberId(Map<String,Integer> args);
   Integer selectCollectPageListCountByMemberId(Integer memberId);
}
