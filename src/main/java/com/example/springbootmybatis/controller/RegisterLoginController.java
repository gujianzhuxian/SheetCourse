package com.example.springbootmybatis.controller;

import com.example.springbootmybatis.mapper.UserMapper;
import com.example.springbootmybatis.pojo.Status;
import com.example.springbootmybatis.pojo.User;
import com.example.springbootmybatis.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@RestController
@Mapper
public class RegisterLoginController {
    @Autowired
    private UserService userService;
    @Resource
    private UserMapper userMapper;

    /**
     * 登录  直接返回状态
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/user/login")
    public Status login(@Parameter(description = "username of the user.") String username, String password) {
        Status status = new Status();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            System.out.println("password = " + password);
            status.setInfo("账号密码不能为空");
//            return "账号密码不能为空";
        } else {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                status.setInfo("账号错误");
//                return "账号错误";
            } else if (!password.equals(user.getPassword())) {
                System.out.println("密码错误 " + user.toString());
                status.setInfo("密码错误");
//                return "密码错误";
            } else {
                status.setUser(user);
                status.setInfo("登陆成功");
//                return "登陆成功";
            }
        }
        return status;
    }
    /**
     * 忘记密码  更改密码成功
     * @param username
     * @return
     */
    @RequestMapping("/user/updatePsd")
    public Status updatePsd(String username,String newPsd) {
        User user=userService.getUserByUsername(username);
        Status status = new Status();
        if (StringUtils.isEmpty(user.getUsername())) {
            status.setInfo("账号不能为空");
//            return "账号不能为空";
        } else if (user.getUsername().length() < 11) {
            status.setInfo("账号长度不合法");
//            return "账号长度不合法";
        } else if (user.getPassword().length() < 6) {
            status.setInfo("密码长度不合法");
//            return "密码长度不合法";
        }{
            try {
                user.setPassword(newPsd);
                userService.updatePsd(user);
                status.setInfo("密码已重置");
//                return "注册成功,请登录";
            } catch (Exception e) {
                status.setInfo("账号不存在");
//                return "账号已经被注册";/
            }
        }
        return status;
    }

    /**
     * 注册页面
     * @param id,username,password,identify
     * @return
     */
    @RequestMapping(value="/user/register",method = RequestMethod.GET)
    public Status register(Integer id,String username,String password,String identify) {
        Status status = new Status();
        int usernamecount=userService.getUserCountByUsername(username);
        if(usernamecount>0){
            status.setInfo("该手机号已存在");
            return status;
        }
        User user=new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setIdentify(identify);
        System.out.println(user.getUsername());
        if (StringUtils.isEmpty(user.getUsername()) ||
                StringUtils.isEmpty(user.getPassword())) {
            status.setInfo("账号密码不能为空");
//            return "账号密码不能为空";
        } else if (user.getUsername().length() < 11) {
            status.setInfo("账号长度不合法");
//            return "账号长度不合法";
        } else if (user.getPassword().length() < 6) {
            status.setInfo("密码长度不合法");
//            return "密码长度不合法";
        }{
            try {
                System.out.println(user+"try");
                userService.addUser(user);
                System.out.println("user = " + user);
                System.out.println("注册成功");
                status.setInfo("注册成功,请登录");
//                return "注册成功,请登录";
            } catch (Exception e) {
                System.out.println("catch");
                status.setInfo("账号已经被注册");
//                return "账号已经被注册";/
            }
        }
        return status;
    }
}
