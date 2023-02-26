package com.example.springbootmybatis.service;

import com.example.springbootmybatis.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserService {
    List<User> findAllUser();
    User getUserByUsername(String username);
    int addUser(User user);
    void updatePsd(User user);
    int getUserCountByUsername(String username);
}
