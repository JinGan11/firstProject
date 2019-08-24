package com.ucar.combination.controller;

import com.ucar.combination.common.Result;
import com.ucar.combination.common.ReturnResult;
import com.ucar.combination.model.Account;
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
import java.util.Calendar;
import java.util.Date;
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
     * @return Result结果集
     * @author peng.zhang11@ucarinc.com
     * @date 2019/7/30 16:10
     * @params loginUser 用户登陆信息
     * @param: session session信息
     */
    @RequestMapping("/login.do_")
    public Result login(@RequestBody(required = false) User loginUser, HttpServletRequest request) {
//        Result result1 = isLogin(request.getSession());
//        int code = (int) result1.get("code");
//        if (202 == code) {
//            return result1;
//        }
        HttpSession session = request.getSession();
        Result result = userService.login(loginUser);
        List<User> list = (List<User>) result.get("list");
        if (result.get("code").equals(200)) {
            loginUser.setId(list.get(0).getId());
            Result resultFirst = userService.isFirstLogin(loginUser);
            //判断时间是否到60天
            Calendar dateOne = Calendar.getInstance();
            Calendar dateTwo = Calendar.getInstance();
            //设置为当前系统时间
            dateOne.setTime(new Date());
            // 获取数据库中的时间
            if (((List<Account>)result.get("accountList")).get(0).getModifyTime() != null) {
                dateTwo.setTime(((List<Account>)result.get("accountList")).get(0).getModifyTime());
            } else {
                dateTwo.setTime(new Date());
            }
            long timeOne = dateOne.getTimeInMillis();
            long timeTwo = dateTwo.getTimeInMillis();
            //转化day
            long day = (timeOne - timeTwo) / (1000 * 60 * 60 * 24);
            //判断账户锁定时间是否大于30分钟
            result = powerService.getAccountAllPermission(list.get(0).getId());
            if (resultFirst.get("code").equals(506)) {
                result.put("code", 506);
            } else {
                result.put("code", 200);
            }
            if (day >= 60) {
                result.put("code", 507);
            }
            session.setAttribute("powerList", result.get("powerList"));
            session.setAttribute("accountName", loginUser.getAccountName());
            session.setAttribute("accountId", list.get(0).getId());
        }
        return result.put("accountName",loginUser.getAccountName());
    }

    /**
     * description: 退出登陆
     *
     * @return
     * @author peng.zhang11@ucarinc.com
     * @date 2019/8/8 11:34
     * @params loginUser 描述
     * @param: request 描述
     */
    @RequestMapping("/logout.do_")
    public Result logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("accountName");
        session.removeAttribute("accountId");
        session.removeAttribute("powerList");
        return Result.ok();
    }

    /**
     * description: 判断是否登陆
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/19 20:20
     * @params
     * @return
     */
    @RequestMapping("/isLogin")
    public Result isLogin(HttpSession session) {
        Long isLogin = (Long) session.getAttribute("accountId");
        if (isLogin != null) {
            Result result = userService.isLogin(isLogin);
            return Result.ok().put("code", 202)
                    .put("accountName", session.getAttribute("accountName"))
                    .put("powerList",session.getAttribute("powerList"));
        }
        return Result.ok().put("code", 300);
    }

    /**
     * description: 判断是否是当前用户
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/19 23:09
     * @params
     * @return
     */
    @RequestMapping("/isCurrentLogin")
    public Result isCurrentLogin(HttpSession session, String accountName) {
        if (session.getAttribute("accountName") != null && session.getAttribute("accountName").equals(accountName)) {
            return Result.ok().put("code", 200);
        }else {
            return Result.ok().put("code", 300)
                    .put("msg", "请重新登陆！");
        }
    }

    /**
     * description: 获取登录信息
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/24 12:09
     * @params session 描述
     * @return
     */
    @RequestMapping("/getLoginInfo.do_")
    public Result getLoginInfo(HttpSession session) {
        if (session.getAttribute("accountName") != null){
            return Result.ok().put("accountName",session.getAttribute("accountName"));
        }
        return Result.ok();
    }
}
