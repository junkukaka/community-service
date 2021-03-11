package com.community.aspn.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.aspn.pojo.community.Community;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CommunityMapper extends BaseMapper<Community> {
    public Map<String,Object> selectCommunityDetail(Integer id);
}
