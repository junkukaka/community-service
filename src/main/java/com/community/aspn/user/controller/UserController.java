package com.community.aspn.user.controller;

import com.community.aspn.pojo.user.User;
import com.community.aspn.user.service.UserService;
import com.community.aspn.util.AjaxResponse;
import com.community.aspn.util.TokenUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins="*",maxAge=3600)
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

    /**
     * @Author nanguangjun
     * @Description //会员登录
     * @Date 16:52 2021/2/23
     * @Param [user]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/users/login")
    public @ResponseBody AjaxResponse logIn(@RequestBody User user){
        User login = userService.login(user);

        Map<String, Object> map = new HashMap<>();
        if(login != null){
            map.put("user",login);
            String token= TokenUtil.sign(login);
            map.put("token",token);
        }else {
            map.put("user", 0);
        }
        return AjaxResponse.success(map);
    }

    /**
     * @Author nanguangjun
     * @Description //获取部门
     * @Date 16:52 2021/2/23
     * @Param []
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/users/department")
    public @ResponseBody AjaxResponse getDepartment(){
        return AjaxResponse.success(userService.getDepartment());
    }

    /**
     * @Author nanguangjun
     * @Description //修改用戶
     * @Date 16:53 2021/2/23
     * @Param [user]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PutMapping("/users")
    public @ResponseBody AjaxResponse updateUser(@RequestBody User user){
        Map<String, String> stringStringMap = userService.updateUser(user);
        return AjaxResponse.success(stringStringMap);
    }

    /**
     * @Author nanguangjun
     * @Description //获取所有用户
     * @Date 16:53 2021/2/23
     * @Param []
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/users/getAll")
    public @ResponseBody AjaxResponse getAllUsers(){
        List<User> allUser = userService.getAllUser();
        return AjaxResponse.success(allUser);
    }


}
