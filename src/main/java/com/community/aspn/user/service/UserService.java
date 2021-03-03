package com.community.aspn.user.service;

import com.community.aspn.pojo.user.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public Map<String,String> insertUser(User user);
    public Map<String,String> updateUser(User user);
    public void deleteUser(Integer id);
    public User getUserById(Integer id);
    public User login(User user);
    public List<User> getAllUser();
    public List<Map<String,Object>> getDepartment();
}
