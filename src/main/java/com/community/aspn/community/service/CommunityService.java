package com.community.aspn.community.service;


import com.community.aspn.pojo.Community;

import java.util.List;
import java.util.Map;

public interface CommunityService {
    int insertCommunity(Community community);
    int updateCommunity(Community community);
    Community selectCommunityById(Integer id);
    int deleteCommunityById(Integer id);
    List<Community> selectAll(Map<String,Object> map);
    Map<String,Object> selectPageList(Map<String,Integer> map);
}
