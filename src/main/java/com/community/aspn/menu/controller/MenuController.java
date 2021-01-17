package com.community.aspn.menu.controller;

import com.community.aspn.menu.service.MenuService;
import com.community.aspn.pojo.Menu;
import com.community.aspn.util.AjaxResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
@CrossOrigin(origins="*",maxAge=3600)
public class MenuController {

    @Resource
    MenuService menuService;

    /**
     * @Author nanguangjun
     * @Description //获取菜单
     * @Date 15:43 2020/12/24
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/menus/{id}")
    public @ResponseBody AjaxResponse getMenuById(@PathVariable Integer id){
        Menu menu = menuService.getMenuById(id);
        return AjaxResponse.success(menu);
    }

    /**
     * @Author nanguangjun
     * @Description // 添加菜单
     * @Date 15:43 2020/12/24
     * @Param [menu]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/menus")
    public @ResponseBody AjaxResponse InsertMenu(@RequestBody Menu menu){
        int m = menuService.insertMenu(menu);
        return AjaxResponse.success(m);
    }

    /**
     * @Author nanguangjun
     * @Description //修改菜单
     * @Date 15:44 2020/12/24
     * @Param [menu]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PutMapping("/menus")
    public @ResponseBody AjaxResponse updateMenu(@RequestBody Menu menu){
        int i = menuService.updateMenu(menu);
        return AjaxResponse.success(i);
    }

    /**
     * @Author nanguangjun
     * @Description //根据条件查询
     * @Date 15:46 2020/12/24
     * @Param []
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/menus/condition")
    public @ResponseBody AjaxResponse getMenusByCondition(@RequestBody Menu menu){
        System.out.print(menu.toString());
        return AjaxResponse.success(menuService.getMenusByCondition(menu));
    }


    @DeleteMapping("/menus/{id}")
    public @ResponseBody AjaxResponse deleteMenuById(@PathVariable Integer id){
        menuService.deleteMenuById(id);
        return AjaxResponse.success();
    }

    @GetMapping("/menus/tree")
    public @ResponseBody AjaxResponse selectMenuTree(@RequestBody Menu menu){
        List<Map<String, Object>> menuTree = menuService.getMenuTree();
        return  AjaxResponse.success(menuTree);
    }



}
