package com.community.aspn.user.controller;

import com.community.aspn.pojo.User;
import com.community.aspn.user.service.UserService;
import com.community.aspn.util.AjaxResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins="*",maxAge=3600)
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/users")
    public @ResponseBody AjaxResponse insertUser(@RequestBody User user){
        userService.insertUser(user);
        return AjaxResponse.success();
    }


}
