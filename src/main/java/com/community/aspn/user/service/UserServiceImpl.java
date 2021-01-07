package com.community.aspn.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.community.aspn.pojo.User;
import com.community.aspn.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author nanguangjun
 * @Description //用户模块
 * @Date 9:22 2020/12/25
 * @Param
 * @return
 **/
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;
    /**
     * @Author nanguangjun
     * @Description // 注册 ， 新增用户使用
     * @Date 9:22 2020/12/25
     * @Param [user]
     * @return int
     **/
    @Override
    public Map<String,String> insertUser(User user) {

        Map<String, String> msg = new HashMap<>();
        //检查邮箱
        Integer email = userMapper.selectCount(new QueryWrapper<User>().eq("email", user.getEmail()));
        if(email>0){
            msg.put("code","0");
            msg.put("msg","邮箱以存在");
            return msg;
        }
        //检查手机
        Integer phone = userMapper.selectCount(new QueryWrapper<User>().eq("phone", user.getPhone()));
        if(phone>0){
            msg.put("code","0");
            msg.put("msg","手机以存在");
            return msg;
        }
        user.setRegisterTime(new Date());
        userMapper.insert(user);
        return msg;
    }

    /**
     * @Author nanguangjun
     * @Description // 跟新用户信息
     * @Date 9:23 2020/12/25
     * @Param [user]
     * @return int
     **/
    @Override
    public int updateUser(User user) {
        return userMapper.updateById(user);
    }

    /**
     * @Author nanguangjun
     * @Description //删除用户
     * @Date 9:24 2020/12/25
     * @Param [id]
     * @return void
     **/
    @Override
    public void deleteUser(Integer id) {
        userMapper.deleteById(id);
    }

    /**
     * @Author nanguangjun
     * @Description //获取用户信息
     * @Date 9:25 2020/12/25
     * @Param [id]
     * @return com.community.aspn.pojo.User
     **/
    @Override
    public User getUserById(Integer id) {
        return userMapper.selectById(id);
    }

    /**
     * @Author nanguangjun
     * @Description // 用户登录
     * @Date 9:25 2020/12/25
     * @Param [user]
     * @return com.community.aspn.pojo.User
     **/
    @Override
    public User login(User user) {
        QueryWrapper<User> query = new QueryWrapper<>();
        //用户名，密码验证
        query.eq("email",user.getEmail())
                .eq("password",user.getPassword());
        return userMapper.selectOne(query);
    }

    /**
     * @Author nanguangjun
     * @Description // 获取所有用户
     * @Date 9:36 2020/12/25
     * @Param []
     * @return java.util.List<com.community.aspn.pojo.User>
     **/
    @Override
    public List<User> getAllUser() {
        return userMapper.selectList(null);
    }
}
