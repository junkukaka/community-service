package com.community.aspn;

import com.community.aspn.menu.mapper.MenuMapper;
import com.community.aspn.menu.service.MenuService;
import com.community.aspn.pojo.Dessert;
import com.community.aspn.pojo.Menu;
import com.community.aspn.test.service.DessertService;
import com.community.aspn.test.service.DessertServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@SpringBootTest
class AspnApplicationTests {

    @Resource
    DessertServiceImpl dessertService;

    @Resource
    MenuMapper menuMapper;

    @Resource
    MenuService menuService;

    @Test
    void contextLoads() {
        Dessert dessert = dessertService.getDessert(6);
        System.out.println(dessert);
    }

    @Test
    void menuSql(){
        Menu menu = menuMapper.selectMenuById2(148);
        System.out.println(menu.toString());
    }

    @Test
    void menuTreeTest(){
        List<Map<String, Object>> menuTree = menuService.getMenuTree();
        for (int i = 0; i < menuTree.size(); i++) {
            Map<String, Object> stringObjectMap = menuTree.get(i);
            System.out.println(stringObjectMap.toString());
        }
    }

}
