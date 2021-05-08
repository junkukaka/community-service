package com.community.aspn.search.service;

import com.community.aspn.pojo.sys.Search;

import java.util.List;
import java.util.Map;

public interface SearchService {
    List<Map<String,Object>> search(Search search);
    List<Search> getSearchContent(String content);
}
