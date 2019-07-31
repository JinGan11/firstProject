package com.ucar.combination.controller;

import com.ucar.combination.common.ReturnResult;
import com.ucar.combination.model.User;
import com.ucar.combination.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: combination
 * @description: 用户Controller
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-07-29 11:40
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/updatePassword")
    public ReturnResult updatePassword(@RequestBody User user) {
        ReturnResult result = userService.updatePassword(user);
        return result;
    }

    @RequestMapping("/getEmpInfo")
    public ReturnResult getEmpInfo(@RequestBody User user) {
        ReturnResult result = userService.getEmpInfo(user);
        return result;
    }

}
