package com.community.aspn.menu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.community.aspn.menu.mapper.MenuMapper;
import com.community.aspn.pojo.Menu;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.*;

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
        int insert = menuMapper.insert(menu);
        return insert;
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
     * @Description //删除菜单
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
     * @Description //根据ID查询
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
     * @Description //条件查询
     * @Date 14:07 2021/1/13
     * @Param [menu]
     * @return java.util.List<com.community.aspn.pojo.Menu>
     **/
    @Override
    public List<Menu> getMenusByCondition(Menu menu) {
        QueryWrapper<Menu> query = new QueryWrapper<>();
        //菜单等级查询
        if(menu.getTier() != null && !"".equals(menu.getTier())){
            query.eq("tier",menu.getTier());
        //添加父目录条件
        }
        if(menu.getFather() != null && !"".equals(menu.getFather())){
            query.eq("father",menu.getFather());
        //使用状态
        }
        if(menu.getUseYn() != null && !"".equals(menu.getUseYn())){
            query.eq("use_yn",menu.getUseYn());
        }

        return menuMapper.selectList(query);


    }

    /**
     * 获取menu
     * @return
     */
    @Override
    public List<Map<String,Object>> getMenuTree() {
        //最终集合
        List<Map<String,Object>> finalList = new ArrayList<>();
        //菜单对象
        List<Map<String,Object>> listMap = new ArrayList<>();

        //菜单临时容器
        List<Menu> listTmp = null;

        Integer id = 0;
        //第一层菜单
        List<Menu> first = menuMapper.selectMenuByTier(1);
        System.out.println(first.size());
        for (int i = 0; i < first.size(); i++) {
            Menu menuTmp = first.get(i);
            //单个对象
            Map<String,Object> mapTmp = new HashMap<>();
            mapTmp.put("id",menuTmp.getId());
            mapTmp.put("tier",menuTmp.getTier());
            mapTmp.put("name",menuTmp.getName());
            listTmp = menuMapper.selectByFather(menuTmp.getId());
            if(!listTmp.isEmpty()){
                mapTmp.put("child",listTmp);
            }
            listMap.add(mapTmp);
        }
        return listMap;
    }
}
