package com.ucar.combination.controller;

import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.Account;
import com.ucar.combination.service.AccountManagerService;
import com.ucar.combination.service.RoleManagementService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    @Resource
    private AccountManagerService accountManagerService;

    @Resource
    private RoleManagementService roleManagementService;

    /*
     * description: 新建账户
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：  2019/8/6
     * @PArams：
     * @Return：
     */
    @ResponseBody
    @RequestMapping("/createAccount.do_")
    public Result createAccount(HttpServletRequest request, HttpSession session){
        String user = (String)session.getAttribute("accountId");
        String accountNum = request.getParameter("accountNum");
        String password = request.getParameter("password");
        String staffId = request.getParameter("staffId");
        String staffNum = request.getParameter("staffNum");
        String permissions = request.getParameter("permissions");
        String secretEmail = request.getParameter("secretEmail");
        String remark = request.getParameter("remark");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountNum", accountNum);
        params.put("password", password);
        params.put("staffId", staffId);
//        params.put("staffNum", staffNum);
        params.put("permissions", permissions);
//        params.put("secretEmail", secretEmail);
        params.put("remark", remark);
        params.put("createEmp", user);
        params.put("modifyEmp", user);
        return null;
    }

    /*
     * description: 获取permission枚举
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：  2019/8/6
     * @PArams：
     * @Return：
     */
    @ResponseBody
    @RequestMapping("/premission.do_")
    public Result premissionList(HttpServletRequest request){
        return new Result().ok().put("permissionList",CommonEnums.toJsonList(CommonEnums.Permission.values()));
    }

    /*
     * description: 账号列表查询
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：  2019/8
     * @PArams：
     * @Return：
     */
    @ResponseBody
    @RequestMapping("/querylist.do_")
    public Result queryList(HttpServletRequest request){
        String page = request.getParameter("page");
        if(page==null){
            page = "1";
        }
        String limit = request.getParameter("limit");
        if(limit==null){
            limit = "10";
        }
        String accountName = request.getParameter("accountName");
        String staffNum = request.getParameter("staffNo");
        String staffName = request.getParameter("name");
        String permissions = request.getParameter("permissions");
        String departmentId = request.getParameter("department");
        String isRelStaff = request.getParameter("isRelStaff");
        String accountState = request.getParameter("status");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        params.put("limit", limit);
        params.put("accountName", accountName);
        params.put("staffNum", staffNum);
        params.put("staffName", staffName);
        params.put("permissions", permissions);
        params.put("departmentId", departmentId);
        params.put("isRelStaff", isRelStaff);
        params.put("accountState", accountState);
        ResultPage resultPage = accountManagerService.queryList(new QueryParam(params));
        return new Result().ok().put("page", resultPage)
                .put("permissionList",CommonEnums.toJsonList(CommonEnums.Permission.values()))
                .put("permissionEnum",CommonEnums.toEnumMap(CommonEnums.Permission.values()))
                .put("accountStatusEnum",CommonEnums.toEnumMap(CommonEnums.AccountStatusEnum.values()))
                .put("accountStatusList",CommonEnums.toJsonList(CommonEnums.AccountStatusEnum.values()));
    }
    /**
     * description:在账户表更改账户状态为无效
     * @author shiya.li@ucarinc.com
     * @date   2019/8/7 9:47
     * @params 账户id
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateStatus.do_")
    public void update(HttpServletRequest request){
        String strid = request.getParameter("accountId");
        int id = Integer.parseInt(strid);
        accountManagerService.updateStatus(id);

    }

    /**
     * description: 获取角色信息
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/5 12:02
     * @params 无
     * @return 角色信息结果集
     */
    @ResponseBody
    @RequestMapping("/getRoleList.do_")
    public Result getRoleList() {
        ResultPage notOwnedRole = roleManagementService.getnotOwnedRoleList();
        ResultPage ownedRole = roleManagementService.getOwnedRoleList();
        return Result.ok().put("notOwnedRole" ,notOwnedRole).put("ownedRole", ownedRole);
    }
}
