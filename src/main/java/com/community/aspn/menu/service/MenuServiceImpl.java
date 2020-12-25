package com.community.aspn.menu.service;

import com.community.aspn.menu.mapper.MenuMapper;
import com.community.aspn.pojo.Menu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService{

    @Resource
    MenuMapper menuMapper;

    
    /**
     * @Author nanguangjun
     * @Description //新增菜单 
     * @Date 15:59 2020/12/24
     * @Param [menu]
     * @return int
     **/
    @Override
    public int insertMenu(Menu menu) {
        menu.setRegisterTime(new Date());
        return menuMapper.insert(menu);
    }
    
    /**
     * @Author nanguangjun
     * @Description // 更新菜单 
     * @Date 15:59 2020/12/24
     * @Param [menu]
     * @return int
     **/
    @Override
    public int updateMenu(Menu menu) {
        menu.setUpdateTime(new Date());
        return menuMapper.updateById(menu);
    }
    
    /**
     * @Author nanguangjun
     * @Description //TODO 
     * @Date 16:00 2020/12/24
     * @Param [id]
     *
     * @return void
     **/
    @Override
    public void deleteMenuById(Integer id) {
        menuMapper.deleteById(id);
    }
    
    /**
     * @Author nanguangjun
     * @Description //TODO 
     * @Date 16:00 2020/12/24
     * @Param [id]
     * @return com.community.aspn.pojo.Menu
     **/
    @Override
    public Menu getMenuById(Integer id) {
        return menuMapper.selectById(id);
    }
    /**
     * @Author nanguangjun
     * @Description //TODO 
     * @Date 16:09 2020/12/24
     * @Param []
     * @return java.util.List<com.community.aspn.pojo.Menu>
     **/
    @Override
    public List<Menu> getAll() {
        return menuMapper.selectList(null);
    }
}
