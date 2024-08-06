package com.community.aspn.community.mapper.service;


import com.community.aspn.pojo.community.Community;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface CommunityService {
    int insertCommunity(Community community);

    int updateCommunity(Community community);

    Community selectCommunityById(Integer id);

    int deleteCommunityById(Integer id);

    List<Community> selectAll(Map<String, Object> map);

    Map<String, Object> selectPageList(Map<String, Integer> map, HttpServletRequest request);

    Map<String, Object> selectCommunityListByMember(Map<String, Integer> map);

    Map<String, Object> selectCommunityDetail(Integer id, HttpServletRequest request);

    List<Map<String, Object>> selectCommunityInMainPage(Map<String, Object> params);

    Integer getCommunityMenuId(Integer id);

    List<Map<String, Object>> selectCommunityTemplatePage(Map<String, Object> params);

    Integer selectCommunityTemplatePageCount(Integer authority);
}
