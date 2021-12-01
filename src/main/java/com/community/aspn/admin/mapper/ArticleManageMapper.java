package com.community.aspn.admin.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleManageMapper {
    List<Map<String,Object>> selectCommunities(Map<String,Object> params);
    Integer selectCommunitiesCnt(Map<String,Object> params);

    List<Map<String,Object>> selectWikis(Map<String,Object> params);
    Integer selectWikisCnt(Map<String,Object> params);

    List<Map<String,Object>> selectWikiHis(Integer wikiId);
}
