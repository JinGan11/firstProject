package com.ucar.combination.controller;

import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.Account;
import com.ucar.combination.model.AccountStaff;
import com.ucar.combination.service.AccountManagerService;
import com.ucar.combination.service.DepartmentService;
import com.ucar.combination.service.MailService;
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

    @Resource
    private DepartmentService departmentService;

    @Resource
    private MailService mailService;

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
            accountStaff.setAccountName(accountStaff.getAccountName().toLowerCase());
            accountStaff.setCreateEmp(user);
            accountStaff.setModifyEmp(user);
            accountManagerService.insertAccount(accountStaff);
            if(accountStaff.getPermissions() == 5){
                accountManagerService.insertDepartmentPower(accountStaff.getAccountId(),accountStaff.getTree());
            }
            if(accountStaff.getStaffId()!= null && accountStaff.getStaffId()!= 0) {
                accountManagerService.updateStaffAccount(accountStaff);
            }
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
    public Result queryList(HttpServletRequest request,HttpSession session){
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
        Long userId = (Long) session.getAttribute("accountId");
        String permissions = request.getParameter("permissions");
        String departmentId = request.getParameter("department");
        String isRelStaff = request.getParameter("isRelStaff");
        String accountState = request.getParameter("status");
        String filterId = request.getParameter("filterIds");
        String[] filterIds = null;
        if(filterId!=null && filterId.length()>0) filterIds=filterId.split(",");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        params.put("limit", limit);
        params.put("accountName", accountName);
        params.put("staffNum", staffNum);
        params.put("staffName", staffName);
        params.put("permissions", permissions);
        params.put("departmentId", departmentId);
        params.put("userId", userId);
        params.put("isRelStaff", isRelStaff);
        params.put("accountState", accountState);
        params.put("filterIds",filterIds);
        ResultPage resultPage = accountManagerService.queryList(new QueryParam(params));
        return new Result().ok().put("page", resultPage)
                .put("permissionList",CommonEnums.toJsonList(CommonEnums.Permission.values()))
                .put("permissionEnum",CommonEnums.toEnumMap(CommonEnums.Permission.values()))
                .put("accountStatusEnum",CommonEnums.toEnumMap(CommonEnums.AccountStatusEnum.values()))
                .put("accountStatusList",CommonEnums.toJsonList(CommonEnums.AccountStatusEnum.values()));
    }

    /**
     * description: 账号列表查询，不含已选中的
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/13 17:22
     * @params
     * @return
     */
    @ResponseBody
    @RequestMapping("/querylist2.do_")
    public Result queryList2(HttpServletRequest request){
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
        String filterId = request.getParameter("filterIds");
        String[] filterIds = filterId.split(",");
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
        params.put("filterIds",filterIds);
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
        //**************************************
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
    //冻结账户信息
    @ResponseBody
    @RequestMapping(value = "/lock",method = RequestMethod.POST)
    public Result lockAccount(@RequestBody Map<String,String> map,HttpServletRequest request){
        String accountId = map.get("id");
        Integer status = 2;
        accountManagerService.lockAndUnlock(Integer.parseInt(accountId),status,"冻结",request);
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/unLock",method = RequestMethod.POST)
    public Result unLockAccount(@RequestBody Map<String,String> map,HttpServletRequest request){
        String accountId = map.get("id");
        Integer status = 1;
        accountManagerService.lockAndUnlock(Integer.parseInt(accountId),status,"解冻",request);
        return null;
    }

    /*
     * description: 修改账户信息
     * @author junqiang.zhang@ucarinc.com
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
            accountStaff.setCreateEmp((Long) session.getAttribute("accountId"));
            if(accountStaff.getStaffId() == null){
                accountStaff.setStaffId(0L);
            }
            accountManagerService.modifyAccount(accountStaff);
            accountManagerService.updateStaffAccount(accountStaff);
            accountManagerService.deleteDepartmentPower(accountStaff.getAccountId());
            if(accountStaff.getPermissions() == 5){
                accountManagerService.insertDepartmentPower(accountStaff.getAccountId(),accountStaff.getTree());
            }
            if(accountStaff.getOldStaffId() != accountStaff.getStaffId() && accountStaff.getOldStaffId() != null){
                accountStaff.setSecretEmail("");
                accountStaff.setStaffId(accountStaff.getOldStaffId());
                accountStaff.setAccountId(0L);
                accountManagerService.updateStaffAccount(accountStaff);
            }
        }catch(Exception e) {
            throw new RuntimeException("");
        }
    }

    /**
     * description: 查询账户名是否存在
     * @author junqiang.zhang@ucarinc.com
     * @date:  2019/8/13 10:41
     * @params: request
     * @return: int 查询到的账户条数
     */
    @ResponseBody
    @RequestMapping("/checkAccountName.do_")
    public int checkAccountName(HttpServletRequest request){
        String accountName = request.getParameter("accountName").toLowerCase();
        return accountManagerService.selectAccountByAccountName(accountName);
    }
    /**
     * description: 查询账户的部门权限
     * @author junqiang.zhang@ucarinc.com
     * @date:  2019/8/10 12:30
     * @params: accountId 账户id
     * @return: List 账户拥有的部门权限的部门id
     */
    @ResponseBody
    @RequestMapping(value = "/selectDeparentPower.do_")
    public Result selectDeparentPower(HttpServletRequest request){
        Long accountId = Long.parseLong(request.getParameter("id"));
        List<String> trees = accountManagerService.selectDeparentPowerByAccountId(accountId);
        Long[] dapartmentId = new Long[trees.size()];
        int i = 0;
        for(String tree : trees){
            dapartmentId[i] = Long.parseLong(tree);
            i++;
        }
        return Result.ok().put("trees",dapartmentId);
    }
    /**
     * description: 删除账户
     * @author junqiang.zhang@ucarinc.com
     * @date:  2019/8/10 14:30
     * @params: accountStaff 账户id
     * @return:
     */
    @Transactional
    @ResponseBody
    @RequestMapping(value = "deleAccount.do_",method = RequestMethod.POST)
    public void deleAccount(@RequestBody AccountStaff accountStaff,HttpSession session){
        accountStaff.setCreateEmp((Long) session.getAttribute("accountId"));
        accountStaff.setModifyEmp((Long) session.getAttribute("accountId"));
        accountManagerService.deleteAccountById(accountStaff);
    }

    @ResponseBody
    @RequestMapping("/resetPass")
    public Result resetPass(@RequestBody Account account, HttpSession session){
        Long OperateAccountId = (Long) session.getAttribute("accountId");
        Account account1 = accountManagerService.selectAccountById(String.valueOf(OperateAccountId));
        String content = "请点击下面的链接重置密码 http://localhost:8082/resetPass 如果无法点击，请复制至浏览器。";
        Result result = new Result();
        AccountStaff accountStaff = new AccountStaff();
        if (account1.getSecretEmail() != null) {
            result = mailService.sendMail(account1.getSecretEmail(),"重置密码",content);
            accountStaff.setAccountId(account1.getId());
            accountStaff.setOperationType("密码重置");
            accountStaff.setStaffId(account1.getStaffId());
            accountStaff.setAccountState(account1.getaccountState());
            accountStaff.setPermissions(account1.getPremissions());
            accountStaff.setStaffNum(account1.getStaffNum());
            accountStaff.setStaffName(account1.getStaffName());
            accountStaff.setCreateEmp(OperateAccountId);
            accountManagerService.insertAccountHistory(accountStaff);
        } else {
            result.put("code", 202);
        }
        return result;
    }
}
