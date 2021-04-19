package com.community.aspn.wiki.service;

import com.community.aspn.pojo.wiki.Wiki;
import com.community.aspn.pojo.wiki.WikiHis;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface WikiService {
    int saveWiki(Map<String,Object> params);
    WikiHis selectWikiHisByID(WikiHis wikiHis);
    List<Wiki> wikiList(Wiki wiki,HttpServletRequest request);
    List<Wiki> wikiListMain(Map<String,Object> params,HttpServletRequest request);
    Map<String,Object> selectWikiDetail(Wiki wiki,HttpServletRequest request);
}
