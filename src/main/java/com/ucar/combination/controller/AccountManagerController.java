package com.ucar.combination.controller;

import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.Account;
import com.ucar.combination.model.AccountStaff;
import com.ucar.combination.service.AccountManagerService;
import com.ucar.combination.service.RoleManagementService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * description: 根据账号id查询account信息
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：  2019/8/6
     * @PArams：
     * @Return：
     */
    @Transactional
    @ResponseBody
    @RequestMapping("/selectAccountById.do_")
    public Result selectAccountById(HttpServletRequest request){
        String id = request.getParameter("id");
        return new Result().ok().put("account",accountManagerService.selectAccountById(id));
    }

    /*
     * description: 新建账户
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：  2019/8/6
     * @PArams：
     * @Return：
     */
    @Transactional
    @ResponseBody
    @RequestMapping(value = "/createAccount.do_", method = RequestMethod.POST)
    public void createAccount(@RequestBody AccountStaff accountStaff, HttpSession session){
        try {
            Long user = (Long) session.getAttribute("accountId");
            accountStaff.setCreateEmp(user);
            accountStaff.setModifyEmp(user);
            Long accountId = accountManagerService.insertAccount(accountStaff);
            accountStaff.setAccountId(accountId);
            accountManagerService.updateStaffAccount(accountStaff);
        }catch(Exception e) {
            throw  new RuntimeException("");
        }
    }

    /*
     * description: 获取permission枚举
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：  2019/8/6
     * @PArams：
     * @Return：
     */
    @ResponseBody
    @RequestMapping("/enums.do_")
    public Result enumsList(HttpServletRequest request){
        return new Result().ok().put("permissionList",CommonEnums.toJsonList(CommonEnums.Permission.values()))
                .put("accountStatusList",CommonEnums.toJsonList(CommonEnums.AccountStatusEnum.values()));
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
        accountManagerService.updateStatus(id,3);

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
    public Result getRoleList(@RequestBody Account account) {
        ResultPage notOwnedRole = roleManagementService.getnotOwnedRoleList(account);
        ResultPage ownedRole = roleManagementService.getOwnedRoleList(account);
        return Result.ok().put("notOwnedRole" ,notOwnedRole).put("ownedRole", ownedRole);
    }
    //冻结
    @ResponseBody
    @RequestMapping(value = "/lock",method = RequestMethod.POST)
    public Result lockAccount(@RequestBody Map<String,String> map){
        String accountId = map.get("id");
        Integer status = 2;
        accountManagerService.updateStatus(Integer.parseInt(accountId),status);
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/unLock",method = RequestMethod.POST)
    public Result unLockAccount(@RequestBody Map<String,String> map){
        String accountId = map.get("id");
        Integer status = 1;
        accountManagerService.updateStatus(Integer.parseInt(accountId),status);
        return null;
    }

    /*
     * description: 修改账户信息
     * @author peng.zhang11@ucarinc.com
     * @date:  2019/8/9 11:00
     * @params: accountStaff
     * @return:
     */
    @Transactional
    @ResponseBody
    @RequestMapping(value = "/modifyAccount.do_",method = RequestMethod.POST)
    public void modifyAccount(@RequestBody AccountStaff accountStaff,HttpSession session){
        try {
            accountStaff.setModifyEmp((Long) session.getAttribute("accountId"));
            accountManagerService.modifyAccount(accountStaff);
            accountManagerService.updateStaffAccount(accountStaff);
            if(accountStaff.getOldStaffId() != accountStaff.getStaffId()){
                accountStaff.setSecretEmail("");
                accountStaff.setStaffId(accountStaff.getOldStaffId());
                accountStaff.setAccountId(0L);
                accountManagerService.updateStaffAccount(accountStaff);
            }
        }catch(Exception e) {
            throw new RuntimeException("");
        }
    }
}
