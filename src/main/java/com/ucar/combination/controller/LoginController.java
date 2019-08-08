package com.ucar.combination.controller;

import com.ucar.combination.common.Result;
import com.ucar.combination.common.ReturnResult;
import com.ucar.combination.model.User;
import com.ucar.combination.service.PowerService;
import com.ucar.combination.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @program: combination
 * @description: 登陆
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-07-29 11:36
 */
@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private PowerService powerService;

    /**
     * description: 用户登陆方法
     * @author peng.zhang11@ucarinc.com
     * @date   2019/7/30 16:10
     * @params loginUser 用户登陆信息
     * @param: session session信息
     * @return Result结果集
     */
    @RequestMapping("/login.do_")
    public Result login(@RequestBody(required = false) User loginUser, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Result result = userService.login(loginUser);
        List<User> list = (List<User>) result.get("list");
        if (result.get("code").equals(200)) {
            result = powerService.getAccountAllPermission(list.get(0).getId());
            result.put("code", 200);
            session.setAttribute("accountName", loginUser.getAccountName());
            session.setAttribute("accountId", list.get(0).getId());
        }
        return result;
    }

    /**
     * description: 退出登陆
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/8 11:34
     * @params loginUser 描述
     * @param: request 描述
     * @return
     */
    @RequestMapping("/logout.do_")
    public Result logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("accountName");
        session.removeAttribute("accountId");
        return Result.ok();
    }
}
