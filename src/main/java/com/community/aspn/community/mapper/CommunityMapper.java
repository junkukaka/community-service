package com.community.aspn.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.aspn.pojo.community.Community;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommunityMapper extends BaseMapper<Community> {
    Map<String,Object> selectCommunityDetail(Integer id);
    List<Map<String,Object>> selectCommunityList(Map<String,Integer> param);
    Integer selectCommunityListCount(Map<String,Integer> param);
}
