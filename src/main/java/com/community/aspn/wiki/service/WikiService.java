package com.community.aspn.wiki.service;

import com.community.aspn.pojo.wiki.Wiki;
import com.community.aspn.pojo.wiki.WikiHis;

import java.util.List;
import java.util.Map;

public interface WikiService {
    WikiHis saveWikiHis(WikiHis wikiHis, Boolean flag);
    Wiki insertWiki(WikiHis wikiHis);
    Wiki activeWikiHis(WikiHis wikiHis);
    WikiHis selectWikiHisByID(Integer id,String remoteAddr);
    Map<String, Object> selectPageList(Map<String, Integer> map, String remoteAddr);
    List<Map<String, Object>> wikiMainList(Map<String,Object> map);
    WikiHis selectWikiDetail(Integer id,String remoteAddr);
    List<Map<String, Object>> selectWikiHisProfile(Integer memberId);
    List<Map<String, Object>> selectWikiHisList(Integer wikiId, String remoteAddr);
    void backToThePastWikiHis(WikiHis wikiHis);
    void deleteWikiHistoryById(Integer id);
    Integer getWikiMenuId(Integer id);
    List<Map<String, Object>> selectWikiEditedProfile(Integer memberId,String remoteAddr);

    List<Map<String, Object>> selectWikiTemplate(Map<String, Object> map);
    Integer selectWikiTemplateCount(Integer authority);

    List<Map<String, Object>> selectWikiHisMembers(Map<String, Object> params);
}
