package com.community.aspn.menu.controller;

import com.community.aspn.menu.service.CommunityMenuService;
import com.community.aspn.pojo.sys.CommunityMenu;
import com.community.aspn.util.AjaxResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/communityMenu")
//@CrossOrigin(origins="*",maxAge=3600)
public class CommunityMenuController {

    @Resource
    CommunityMenuService communityMenuService;

    /**
     * @Author nanguangjun
     * @Description //获取菜单
     * @Date 15:43 2020/12/24
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/menus/{id}")
    public @ResponseBody AjaxResponse getMenuById(@PathVariable Integer id){
        CommunityMenu communityMenu = communityMenuService.getMenuById(id);
        return AjaxResponse.success(communityMenu);
    }

    /**
     * @Author nanguangjun
     * @Description // 添加菜单
     * @Date 15:43 2020/12/24
     * @Param [menu]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/menus")
    public @ResponseBody AjaxResponse InsertMenu(@RequestBody CommunityMenu communityMenu){
        int m = communityMenuService.insertMenu(communityMenu);
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
    public @ResponseBody AjaxResponse updateMenu(@RequestBody CommunityMenu communityMenu){
        int i = communityMenuService.updateMenu(communityMenu);
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
    public @ResponseBody AjaxResponse getMenusByCondition(@RequestBody CommunityMenu communityMenu){
        System.out.print(communityMenu.toString());
        return AjaxResponse.success(communityMenuService.getMenusByCondition(communityMenu));
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
        communityMenuService.deleteMenuById(id);
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
    public @ResponseBody AjaxResponse selectMenuTree(){
        List<Map<String, Object>> menuTree = communityMenuService.getMenuTree();
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
    public @ResponseBody AjaxResponse sortMenu(@RequestBody List<CommunityMenu> list){
        for (int i = 0; i < list.size(); i++) {
            communityMenuService.updateMenu(list.get(i));
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
        return AjaxResponse.success(communityMenuService.getDashboard(menuId));
    }



}
