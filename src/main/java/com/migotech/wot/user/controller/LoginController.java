package com.migotech.wot.user.controller;

import com.migotech.wot.common.model.Result;
import com.migotech.wot.user.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @PostMapping(value="/login")
    public Object login(String name, String password){
        boolean rememberMe = true;
        UsernamePasswordToken token = new UsernamePasswordToken(name, password,rememberMe);
        try {
            SecurityUtils.getSubject().login(token);
        }catch (Exception e){
            return Result.failed("用户名密码错误").put("exception", e.getMessage());
        }
        return Result.success();
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public Object userInfo() {
        User loginOne = (User) SecurityUtils.getSubject().getPrincipal();
        return Result.success().put("user", loginOne);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Object logout() {
        SecurityUtils.getSubject().logout();
        return Result.success();
    }

}
