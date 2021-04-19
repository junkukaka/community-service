package com.community.aspn.menu.service;

import com.community.aspn.pojo.sys.WikiMenu;

import java.util.List;
import java.util.Map;

public interface WikiMenuService {
    public int insertMenu(WikiMenu wikiMenu);
    public int updateMenu(WikiMenu wikiMenu);
    public void deleteMenuById(Integer id);
    public WikiMenu getMenuById(Integer id);
    public List<WikiMenu> getMenusByCondition(WikiMenu wikiMenu);
    public List<Map<String,Object>> getMenuTree();
}
