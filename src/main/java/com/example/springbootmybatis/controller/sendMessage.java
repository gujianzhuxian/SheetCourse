package com.example.springbootmybatis.controller;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.example.springbootmybatis.pojo.Status;
import com.example.springbootmybatis.pojo.User;
import com.example.springbootmybatis.service.UserService;
import com.example.springbootmybatis.service.impl.AliyunSmsSenderServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author: LX 17839193044@162.com
 * @Description: 测试发送短信controller
 * @Date: 14:00 2019/4/18
 * @Version: V1.0
 */
@RestController
public class sendMessage {
    @Autowired
    private UserService userService;
    @Autowired
    private AliyunSmsSenderServiceImpl aliyunSmsSenderServiceImpl;
    private SendSmsResponse sendSmsResponse;

    /**
     * @Author: wxw qaz110258357@163.com
     * @Description: 短信发送
     * @Date: 2022/2/3 16:08
     * @Version: V1.0
     */
    @GetMapping("/sms")
    public Status sms(@Parameter(description = "get verifycode") String username) {
        Status status = new Status();
//        User user = userService.getUserByUsername(username);
//        此部分由重置密码或注册账号的逻辑部分进行把关
//        if (user == null) {
//            status.setInfo("账号不存在");
//            return status;
////                return "账号错误";
//        }
        // 生成4位随机数字的验证码
        String netVerifycode= String.format("%04d", new Random().nextInt(9999));
        Map<String, String> map = new HashMap<>();
        map.put("code", netVerifycode);
        sendSmsResponse = aliyunSmsSenderServiceImpl.sendSms(username,
                JSON.toJSONString(map),
                "SMS_269035264");
        //status.setInfo(JSON.toJSONString(sendSmsResponse));
        if(sendSmsResponse.getMessage().equals("OK")){
            status.setInfo(netVerifycode);
        }else {
            status.setInfo("验证码发送失败");
        }
        return status;
    }

    /**
     * @Author: LX 17839193044@162.com
     * @Description: 短信查询
     * @Date: 2019/4/18 16:08
     * @Version: V1.0
     */
    @GetMapping("/getSms")
    public Status query(@Parameter(description = "Query verification code") String username) {
        Status status=new Status();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            status.setInfo("账号不存在");
            return status;
//                return "账号错误";
        }
        QuerySendDetailsResponse querySendDetailsResponse = aliyunSmsSenderServiceImpl.querySendDetails(sendSmsResponse.getBizId(),
                user.getUsername(), 10L, 1L);
        status.setInfo(querySendDetailsResponse.getMessage());
        return status;
    }
}

