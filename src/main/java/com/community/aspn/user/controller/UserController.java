package com.community.aspn.user.controller;

import com.community.aspn.pojo.User;
import com.community.aspn.user.service.UserService;
import com.community.aspn.util.AjaxResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins="*",maxAge=3600)
public class UserController {

    @Resource
    UserService userService;

    /**
     * @Author nanguangjun
     * @Description // 会员注册
     * @Date 16:03 2021/1/7
     * @Param [user]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/users")
    public @ResponseBody AjaxResponse insertUser(@RequestBody User user){
        Map<String, String> stringStringMap = userService.insertUser(user);
        return AjaxResponse.success(stringStringMap);
    }

    /**
     * @Author nanguangjun
     * @Description // 获取用户
     * @Date 16:03 2021/1/7
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/users/{id}")
    public @ResponseBody AjaxResponse getUserById(@PathVariable int id){
        User userById = userService.getUserById(id);
        return AjaxResponse.success(userById);
    }

    @PostMapping("/users/login")
    public @ResponseBody AjaxResponse logIn(@RequestBody User user){
        User login = userService.login(user);
        return AjaxResponse.success(login);
    }

    @GetMapping("/department")
    public @ResponseBody AjaxResponse getDepartment(){
        return AjaxResponse.success(userService.getDepartment());
    }


}
