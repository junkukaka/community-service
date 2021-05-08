package com.community.aspn.search.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.aspn.pojo.sys.Search;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SearchMapper  extends BaseMapper<Search> {
    List<Map<String,Object>> wikiSearch(String content);
    List<Map<String,Object>> communitySearch(String content);
    void updateSearch(Search search);
}
