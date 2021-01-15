package com.community.aspn;

import com.community.aspn.menu.service.MenuService;
import com.community.aspn.pojo.Dessert;
import com.community.aspn.pojo.Menu;
import com.community.aspn.test.service.DessertService;
import com.community.aspn.test.service.DessertServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class AspnApplicationTests {

    @Resource
    DessertServiceImpl dessertService;

    @Resource
    MenuService menuService;

    @Test
    void contextLoads() {
        Dessert dessert = dessertService.getDessert(1);
        System.out.println(dessert);
    }

    @Test
    void testMenu(){
        List<Menu> menuTree = menuService.getMenuTree();
        for (Menu menu: menuTree) {
            System.out.printf(menu.getName());
        }
    }


}
