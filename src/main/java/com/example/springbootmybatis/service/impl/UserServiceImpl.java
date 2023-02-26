package com.example.springbootmybatis.service.impl;

import com.example.springbootmybatis.mapper.UserMapper;
import com.example.springbootmybatis.pojo.User;
import com.example.springbootmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Component
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> findAllUser() {
        List<User> allUser = userMapper.findAllUser();
        return allUser;
    }
    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public void updatePsd(User user){
        userMapper.updatePsd(user);
    }

    @Override
    public int getUserCountByUsername(String username){
        return userMapper.getUserCountByUsername(username);
    }


}
