package com.community.aspn.menu.service;

import com.community.aspn.pojo.sys.CommunityMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CommunityMenuService {
    int insertMenu(CommunityMenu communityMenu);

    int updateMenu(CommunityMenu communityMenu);

    void deleteMenuById(Integer id);

    CommunityMenu getMenuById(Integer id);

    List<CommunityMenu> getMenusByCondition(CommunityMenu communityMenu);

    List<Map<String, Object>> getMenuTree();

    List<Map<String, Object>> getDashboard(Integer menuId);
}
