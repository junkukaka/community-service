package com.community.aspn.menu.service;

import com.community.aspn.pojo.sys.CommunityMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CommunityMenuService {
    public int insertMenu(CommunityMenu communityMenu);
    public int updateMenu(CommunityMenu communityMenu);
    public void deleteMenuById(Integer id);
    public CommunityMenu getMenuById(Integer id);
    public List<CommunityMenu> getMenusByCondition(CommunityMenu communityMenu);
    public List<Map<String,Object>> getMenuTree();
}
