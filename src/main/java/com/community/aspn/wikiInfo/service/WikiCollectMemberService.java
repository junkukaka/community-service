package com.community.aspn.wikiInfo.service;


import com.community.aspn.pojo.wiki.WikiCollect;
import com.community.aspn.pojo.wiki.WikiMemberCollectMenu;

import java.util.List;
import java.util.Map;

public interface WikiCollectMemberService {
    void saveCollectWikiMemberCollectMenu(WikiMemberCollectMenu wikiMemberCollectMenu);
    void updateCollectWikiMemberCollectMenu(WikiMemberCollectMenu wikiMemberCollectMenu);
    void deleteCollectWikiMemberCollectMenu(Integer id);
    List<WikiMemberCollectMenu> selectCollectWikiMemberCollectMenu(Integer memberId);

    void saveWikiCollect(WikiCollect wikiCollect);
    List<Map<String,Object>> selectWikiCollect(Integer id);
    List<Map<String,Object>> selectAllWikiCollect(Integer id);
    void deleteWikiCollect(Map<String,Integer> params);

}
