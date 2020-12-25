package com.community.aspn.user.service;

import com.community.aspn.pojo.User;

import java.util.List;

public interface UserService {
    public int insertUser(User user);
    public int updateUser(User user);
    public void deleteUser(Integer id);
    public User getUserById(Integer id);
    public User login(User user);
    public List<User> getAllUser();
}
