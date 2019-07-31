package com.ucar.combination.controller;

import com.ucar.combination.common.Result;
import com.ucar.combination.common.ReturnResult;
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

    /**
     * description: 用户登陆方法
     * @author peng.zhang11@ucarinc.com
     * @date   2019/7/30 16:10
     * @params loginUser 用户登陆信息
     * @param: session session信息
     * @return Result结果集
     */
    @RequestMapping("/login.do_")
    public ReturnResult login(@RequestBody(required = false) LoginUser loginUser, HttpSession session) {
        ReturnResult result = userService.login(loginUser);
        if (result.getCode() == 200) {
            session.setAttribute("username", loginUser.getAccountName());
        }
        return result;
    }
}
