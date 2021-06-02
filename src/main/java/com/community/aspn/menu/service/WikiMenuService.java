package com.community.aspn.menu.service;

import com.community.aspn.pojo.sys.WikiMenu;

import java.util.List;
import java.util.Map;

public interface WikiMenuService {
    int insertMenu(WikiMenu wikiMenu);

    int updateMenu(WikiMenu wikiMenu);

    void deleteMenuById(Integer id);

    WikiMenu getMenuById(Integer id);

    List<WikiMenu> getMenusByCondition(WikiMenu wikiMenu);

    List<Map<String, Object>> getMenuTree();

    List<Map<String, Object>> getDashboard(Integer menuId);
}
