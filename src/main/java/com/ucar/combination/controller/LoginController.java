package com.ucar.combination.controller;

import com.ucar.combination.common.Result;
import com.ucar.combination.model.LoginUser;
import com.ucar.combination.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @program: combination
 * @description: 登陆
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-07-29 11:36
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public Result login(@RequestBody(required = false) LoginUser loginUser, HttpSession session) {
        session.setAttribute("username", loginUser.getAccountName());
        return userService.login(loginUser);
    }
}
