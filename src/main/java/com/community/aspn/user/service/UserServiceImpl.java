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
        Map<String, String> msg = checkUserOne(user);
        //회원 핸드폰 메일 체크
        if("0".equals(msg.get("code"))){
            return msg;
        }
        user.setRegisterTime(new Date());
        userMapper.insert(user);
        msg.put("code","1");
        msg.put("msg","회원가입 성공");
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
    public Map<String,String> updateUser(User user) {
        Map<String, String> msg = checkUserOne(user);
        //회원 핸드폰 메일 체크
        if("0".equals(msg.get("code"))){
            return msg;
        }
        user.setRegisterTime(new Date());
        userMapper.updateById(user);
        msg.put("code","1");
        msg.put("msg","회원정보 수정 성공");
        return msg;
    }

    /**
     * @Author nanguangjun
     * @Description //회원 핸드폰 메일 체크
     * @Date 11:09 2021/2/19
     * @Param [user]
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    public Map<String,String> checkUserOne(User user){
        Map<String, String> msg = new HashMap<>();
        //检查邮箱
        Integer email = userMapper.selectCount(new QueryWrapper<User>().eq("email", user.getEmail()));
        if(email> 0){
            msg.put("code","0");
            msg.put("msg","중복된 메일 주소 입니다.");
            return msg;
        }
        //检查手机
        Integer phone = userMapper.selectCount(new QueryWrapper<User>().eq("phone", user.getPhone()));
        if(phone> 0){
            msg.put("code","0");
            msg.put("msg","중복된 핸드폰 번호 입니다.");
            return msg;
        }
        return msg;
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
        User u = userMapper.selectOne(query);
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

    @Override
    public List<Map<String,Object>> getDepartment() {
        return userMapper.getDepartment();
    }
}
