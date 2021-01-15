package com.community.aspn.menu.service;

import com.community.aspn.pojo.Menu;

import java.util.List;

public interface MenuService {
    public int insertMenu(Menu menu);
    public int updateMenu(Menu menu);
    public void deleteMenuById(Integer id);
    public Menu getMenuById(Integer id);
    public List<Menu> getMenusByCondition(Menu menu);
    public List<Menu> getMenuTree();
}
