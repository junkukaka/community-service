package com.community.aspn.wiki.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.aspn.pojo.wiki.Wiki;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface WikiMapper extends BaseMapper<Wiki> {
    //insert wiki and return primary key
    Integer addWiki(Wiki wiki);
    List<Map<String,Object>> selectWikiList(Map<String,Object> args);
    Integer selectWikiListCount(Map<String,Object> args);
    List<Map<String,Object>> selectWikiMainList(Map<String,Object> args);
    List<Map<String,Object>> selectWikiHisList(Integer wikiId);
    List<Map<String,Object>> selectWikiHisProfile(Integer memberId);
    List<Map<String,Object>> selectWikiEditedProfile(Integer memberId);
    List<Map<String, Object>> selectWikiTemplate(Map<String, Object> params);
    Integer selectWikiTemplateCount(Map<String, Object> params);
    List<Map<String, Object>> selectWikiHisMembers(Integer wikiId);
    void saveWikiRating(HashMap<String, Object> param);
}
