package com.community.aspn.wiki.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.aspn.pojo.wiki.Wiki;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WikiMapper extends BaseMapper<Wiki> {
}
