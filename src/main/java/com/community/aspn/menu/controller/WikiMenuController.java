package com.community.aspn.menu.controller;

import com.community.aspn.menu.service.WikiMenuService;
import com.community.aspn.pojo.sys.WikiMenu;
import com.community.aspn.util.AjaxResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wikiMenu")
public class WikiMenuController {

    @Resource
    WikiMenuService wikiMenuService;

    /**
     * @Author nanguangjun
     * @Description //获取菜单
     * @Date 15:43 2020/12/24
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/menus/{id}")
    public @ResponseBody AjaxResponse getMenuById(@PathVariable Integer id){
        WikiMenu wikiMenu = wikiMenuService.getMenuById(id);
        return AjaxResponse.success(wikiMenu);
    }

    /**
     * @Author nanguangjun
     * @Description // 添加菜单
     * @Date 15:43 2020/12/24
     * @Param [menu]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/menus")
    public @ResponseBody AjaxResponse InsertMenu(@RequestBody WikiMenu wikiMenu){
        int m = wikiMenuService.insertMenu(wikiMenu);
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
    public @ResponseBody AjaxResponse updateMenu(@RequestBody WikiMenu wikiMenu){
        int i = wikiMenuService.updateMenu(wikiMenu);
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
    public @ResponseBody AjaxResponse getMenusByCondition(@RequestBody WikiMenu wikiMenu){
        System.out.print(wikiMenu.toString());
        return AjaxResponse.success(wikiMenuService.getMenusByCondition(wikiMenu));
    }

    /**
     * @Author nanguangjun
     * @Description // 删除
     * @Date 9:14 2021/1/18
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @DeleteMapping("/menus/{id}")
    public @ResponseBody AjaxResponse deleteMenuById(@PathVariable Integer id){
        wikiMenuService.deleteMenuById(id);
        return AjaxResponse.success();
    }

    /**
     * @Author nanguangjun
     * @Description //获取menu tree
     * @Date 9:14 2021/1/18
     * @Param []
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/menus/tree")
    public @ResponseBody AjaxResponse selectMenuTree(HttpServletRequest httpServletRequest){
        Map<String, Integer> params = new HashMap<>();
        Integer authority = Integer.parseInt(httpServletRequest.getParameter("authority"));
        params.put("authority",authority);
        //0:viewYn ; 1:editYn
        Integer flag = Integer.parseInt(httpServletRequest.getParameter("flag"));
        params.put("flag",flag);
        List<Map<String, Object>> menuTree = wikiMenuService.getMenuTree(params);
        return  AjaxResponse.success(menuTree);
    }

    /**
     * @Author nanguangjun
     * @Description // 获取所有menu
     * @Date 14:52 2021/7/9
     * @Param []
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/menus/getAllMenuTree")
    public @ResponseBody AjaxResponse getAllMenuTree(){
        List<Map<String, Object>> menuTree = wikiMenuService.getAllMenuTree();
        return  AjaxResponse.success(menuTree);
    }

    /**
     * @Author nanguangjun
     * @Description //修改菜单顺序
     * @Date 16:21 2021/4/12
     * @Param [list]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/sortMenu")
    public @ResponseBody AjaxResponse sortMenu(@RequestBody List<WikiMenu> list){
        for (int i = 0; i < list.size(); i++) {
            wikiMenuService.updateMenu(list.get(i));
        }
        return AjaxResponse.success();
    }

    /**
     * @Author nanguangjun
     * @Description // get dashboard by menuId
     * @Date 14:00 2021/6/1
     * @Param [menuId]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/dashboard/{menuId}")
    public @ResponseBody AjaxResponse getDashboard(@PathVariable Integer menuId){
        return AjaxResponse.success(wikiMenuService.getDashboard(menuId));
    }



}
