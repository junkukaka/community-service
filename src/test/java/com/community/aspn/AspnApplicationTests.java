package com.community.aspn;

import com.community.aspn.pojo.Dessert;
import com.community.aspn.test.service.DessertService;
import com.community.aspn.test.service.DessertServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AspnApplicationTests {

    @Resource
    DessertServiceImpl dessertService;

    @Test
    void contextLoads() {
        Dessert dessert = dessertService.getDessert(1);
        System.out.println(dessert);
    }


}
