package com.ucar.combination.controller;

import com.ucar.combination.common.Result;
import com.ucar.combination.model.*;
import com.ucar.combination.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;

/**
 * @version 1.0
 * @description: 权限控制类
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-08-05 16:40
 */
@RestController
@RequestMapping("/power")
public class PowerController {

    @Autowired
    PowerService powerService;

    /**
     * description: 获取所有权限信息
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/5 22:40
     * @params 无
     * @return 返回结果集
     */
    @RequestMapping("/getPowerList")
    public Result getPowerList() {
        PowerTree rootNode = powerService.buildPowerTree();
        return new Result().put("powerTree", rootNode);
    }

    /**
     * description: 获取账号已有的权限
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/7 14:59
     * @params
     * @return
     */
    @RequestMapping("/getAccountPower.do_")
    public Result getAccountPower(@RequestBody Account account) {
        return powerService.getAccountPower(account);
    }

    /**
     * description: 为账号添加角色
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/5 22:40
     * @params role 角色
     * @return
     */
    @RequestMapping("/modifyAccountRole")
    public Result modifyAccountRole(@RequestBody RoleList roleList, HttpSession session) {
        Long accountId = (Long) session.getAttribute("accountId");
        Result result = powerService.modifyAccountRole(roleList,accountId);
        return result;
    }

    /**
     * description: 修改账户特殊权限
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/5 22:51
     * @params
     * @return
     */
    @RequestMapping("/modifySpecialPower")
    public Result modifySpecialPower(@RequestBody PowerList powerList, HttpSession session) {
        Long accountId = (Long) session.getAttribute("accountId");
        Result result = powerService.modifySpecialPower(powerList,accountId);
        return result;
    }

}
