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
    List<Map<String, Object>> wikiList(Wiki wiki, String remoteAddr);
    List<Map<String, Object>> wikiMainList(Integer count, String remoteAddr);
    WikiHis selectWikiDetail(Integer id,String remoteAddr);
    List<Map<String, Object>> selectWikiHisProfile(Integer memberId);
    List<Map<String, Object>> selectWikiHisList(Integer wikiId, String remoteAddr);
    void backToThePastWikiHis(WikiHis wikiHis);
    void deleteWikiHistoryById(Integer id);
}
