package com.community.aspn.wiki.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.aspn.pojo.wiki.Wiki;
import org.apache.ibatis.annotations.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Mapper
public interface WikiMapper extends BaseMapper<Wiki> {
    //insert wiki and return primary key
    Integer addWiki(Wiki wiki);
    List<Map<String,Object>> selectWikiList(Wiki wiki);
    List<Map<String,Object>> selectWikiMainList(Integer count);
}
