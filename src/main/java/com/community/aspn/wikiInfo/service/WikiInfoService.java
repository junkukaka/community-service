package com.community.aspn.wikiInfo.service;



import com.community.aspn.pojo.wiki.WikiCollect;
import com.community.aspn.pojo.wiki.WikiHits;
import com.community.aspn.pojo.wiki.WikiLike;

import java.util.Map;

public interface WikiInfoService {
     void saveHits(WikiHits wikiHits);
     int saveCollete(WikiCollect wikiCollect);
     int saveLikes(WikiLike wikiLike);
     Map<String,Integer> selectWikiInfoCountByCommunityId(Integer wikiId);
     Map<String,Integer> selectLikeAndCollectByMember(Map<String,Integer> map);
     Map<String,Object> selectLikesPageListByMemberId(Map<String,Integer> params);
     Map<String,Object> selectCollectPageListByMemberId(Map<String,Integer> params);
}
