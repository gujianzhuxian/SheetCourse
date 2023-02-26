package com.example.springbootmybatis.controller;

import com.example.springbootmybatis.mapper.UserMapper;
import com.example.springbootmybatis.pojo.User;
import com.example.springbootmybatis.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    private UserService userService;
    @Resource
    private UserMapper userMapper;

    @Operation(summary = "测试：获取所有用户信息")
    @GetMapping("/test")
    public List<User> getAllUser(){
        List<User> allUser = userService.findAllUser();
        return allUser;
    }


}
