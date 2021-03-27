package com.community.aspn.comInfo.service;


import com.community.aspn.pojo.community.ComCollect;
import com.community.aspn.pojo.community.ComHits;
import com.community.aspn.pojo.community.ComLikes;

import java.util.Map;

public interface ComInfoService {
     void saveHits(ComHits comHits);
     int saveCollete(ComCollect comCollect);
     int saveLikes(ComLikes comLikes);
     Map<String,Integer> selectComInfoCountByCommunityId(Integer communityId);
     Map<String,Integer> selectLikeAndCollectByMember(Map<String,Integer> map);
     Map<String,Object> selectLikesPageListByMemberId(Map<String,Integer> params);
     Map<String,Object> selectCollectPageListByMemberId(Map<String,Integer> params);
}
