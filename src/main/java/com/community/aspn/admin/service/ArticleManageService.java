package com.community.aspn.admin.service;


import java.util.List;
import java.util.Map;


public interface ArticleManageService {
    Map<String,Object> selectCommunities(Map<String,Object> params);
    Map<String,Object> selectWikis(Map<String,Object> params);
    List<Map<String,Object>> selectWikiHis(Map<String,Object> params);

    void deleteWikiHis(Integer id);
    void deleteWiki(Integer id);
}
