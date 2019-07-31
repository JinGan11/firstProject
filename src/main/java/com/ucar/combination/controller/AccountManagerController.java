package com.ucar.combination.controller;

import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.Result;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:账户管理
 *
 * @author junqiang.zhang@ucarinc.com
 * @version 1.0
 * @date: 2019/7/30
 **/
@Controller
@RequestMapping("/account")
public class AccountManagerController {

    @ResponseBody
    @RequestMapping("/permission.do_")
    public Result permissionList() {
        return new Result().ok().put("permissionList",CommonEnums.toJsonList(CommonEnums.Permission.values()));
    }

//    @ResponseBody
//    @RequestMapping("/querylist.do_")
//    public Result queryList(){
//
//    }
}
