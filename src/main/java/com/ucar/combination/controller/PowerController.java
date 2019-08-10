package com.ucar.combination.controller;

import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.*;
import com.ucar.combination.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    /**
     * description:用于接收数据返回前端列表
     *
     * @param request 前端请求
     * @return com.ucar.combination.common.Result 返回数据
     * @author gan.jin@ucarinc.com
     * @date 2019/8/7 15:18
     */

    /*@ResponseBody
    @RequestMapping("/queryRolePowerlist.do_")
    public Result list(HttpServletRequest request) {
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String roleName = request.getParameter("roleName");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        params.put("limit", limit);
        params.put("role_name", roleName);
        ResultPage resultPage = powerService.queryRolePowerList(new QueryParam(params));

        return Result.ok().put("page", resultPage).put("RoleStatusEnum", CommonEnums.toEnumMap(CommonEnums.RoleStatusEnum.values())).put("roleList",roleList);
    }*/

}
