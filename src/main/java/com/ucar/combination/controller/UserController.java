package com.ucar.combination.controller;

import com.ucar.combination.common.ReturnResult;
import com.ucar.combination.model.User;
import com.ucar.combination.model.UpdateUserPwd;
import com.ucar.combination.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

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

    /**
     * description: 修改密码
     * @author peng.zhang11@ucarinc.com
     * @date   2019/7/31 16:52
     * @params user 存储前台传入的用户信息以及要修改的密码
     * @return 结果集
     */
    @RequestMapping("/updatePassword")
    public ReturnResult updatePassword(@RequestBody UpdateUserPwd userPwdVo, HttpSession session) {
        //通过session获取用户的Id
        Long accountId = (Long) session.getAttribute("accountId");
        userPwdVo.setId(accountId);
        ReturnResult result = userService.updatePassword(userPwdVo);
        return result;
    }

    /**
     * description: 获得员工的具体信息
     * @author peng.zhang11@ucarinc.com
     * @date   2019/7/31 16:53
     * @params user 存储员工账户名
     * @return 返回结果集
     */
    @RequestMapping("/getEmpInfo")
    public ReturnResult getEmpInfo(@RequestBody User user) {
        ReturnResult result = userService.getEmpInfo(user);
        return result;
    }

}
