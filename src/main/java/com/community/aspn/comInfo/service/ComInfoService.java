package com.community.aspn.comInfo.service;


import com.community.aspn.pojo.community.ComCollect;
import com.community.aspn.pojo.community.ComHits;
import com.community.aspn.pojo.community.ComLikes;

import java.util.Map;

public interface ComInfoService {
    public void saveHits(ComHits comHits);
    public int saveCollete(ComCollect comCollect);
    public int saveLikes(ComLikes comLikes);
    public Map<String,Integer> selectComInfoCountByCommunityId(Integer communityId);
}
