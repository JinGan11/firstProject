package com.ucar.combination.controller;

import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.AccountManageDao;
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

import static com.ucar.combination.common.StaticValues.LOCALHOST;

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
    private AccountManageDao accountState;

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
        return new Result().ok().put("account",accountManagerService.selectAccountById(id)).put("permissionEnum",CommonEnums.toEnumMap(CommonEnums.Permission.values())).put("accountStatusEnum",CommonEnums.toEnumMap(CommonEnums.AccountStatusEnum.values()));
    }
    /**
     * description: 账号功能权限明细，根据账户ID进行查找
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/20 10:07
     * @params request 描述

     * @return
     */
	@ResponseBody
	@RequestMapping("/selectById.do_")
	public Result selectById(HttpServletRequest request){
		String id = request.getParameter("id");
		return new Result().ok().put("account",accountManagerService.selectById(Long.valueOf(id))).put("permissionEnum",CommonEnums.toEnumMap(CommonEnums.Permission.values())).put("accountStatusEnum",CommonEnums.toEnumMap(CommonEnums.AccountStatusEnum.values()));
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
    public Result createAccount(@RequestBody AccountStaff accountStaff, HttpSession session){
        //获取员工是否删除，离职和关联账户的信息
        int flag = accountManagerService.getStaffInfBystaffId(accountStaff.getStaffId());
        if(flag ==0) {
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
        }
        return Result.ok().put("flag",flag);
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
        Map<String, Object> params = new HashMap<String, Object>();
        if(page==null){
            page = "1";
        }
        String limit = request.getParameter("limit");
        if(limit==null){
            limit = "10";
        }
        if(request.getParameter("defaultStatus") != null && (request.getParameter("status")==""||request.getParameter("status")==null)) {
            params.put("defaultStatus", "12");
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
        String flag = request.getParameter("flag");
        String[] filterIds = null;
        if(filterId!=null && filterId.length()>0) filterIds=filterId.split(",");
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
        params.put("flag",flag);
        ResultPage resultPage = accountManagerService.queryList(new QueryParam(params));
        List<Account> accountDtoList = accountManagerService.getAccountList(new QueryParam(params));
        return new Result().ok().put("page", resultPage)
                .put("accountDtoList",accountDtoList)
                .put("permissionList",CommonEnums.permissionsJsonList(CommonEnums.Permission.values()))
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

    //冻结账户信息
    @ResponseBody
    @RequestMapping(value = "/lock",method = RequestMethod.POST)
    public Result lockAccount(@RequestBody Map<String,String> map,HttpServletRequest request){
        String accountId = map.get("id");
        if (accountState.getAccountStateById(Long.parseLong(accountId)) == 1){
        Integer status = 2;
        accountManagerService.lockAndUnlock(Integer.parseInt(accountId),status,"冻结",request);
        return null;
        }else if(accountState.getAccountStateById(Long.parseLong(accountId)) == 2){
            return Result.error(20,"账号状态已经是冻结，冻结失败");
        }else {
            return Result.error(30,"账号已失效，冻结失败");
        }
    }
    //解冻账户信息
    @ResponseBody
    @RequestMapping(value = "/unLock",method = RequestMethod.POST)
    public Result unLockAccount(@RequestBody Map<String,String> map,HttpServletRequest request){
        String accountId = map.get("id");
        if (accountState.getAccountStateById(Long.parseLong(accountId)) == 2){
            Integer status = 1;
            accountManagerService.lockAndUnlock(Integer.parseInt(accountId),status,"解冻",request);
            return null;
        }else if(accountState.getAccountStateById(Long.parseLong(accountId)) == 1){
            return Result.error(10,"账号状态已经是正常，解冻失败");
        }else {
            return Result.error(30,"账号已失效，解冻失败");
        }
    }

    //历史记录
    @ResponseBody
    @RequestMapping("/accountHistory")
        public Result getAccountHistoryList(@RequestBody Map<String,String> map ,HttpServletRequest request){
        String accountId = map.get("id");
        List historyList = accountManagerService.getHistoryList(accountId);
        return Result.ok().put("historyList" ,historyList);
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
    public Result modifyAccount(@RequestBody AccountStaff accountStaff,HttpSession session){
        //获取账户是否删除的信息
        int state = accountManagerService.getAccountStateById(accountStaff.getAccountId());
        //获取员工是否删除，离职和关联账户的信息
        int flag = accountManagerService.getStaffInfBystaffId(accountStaff.getStaffId());
        //防止并发修改关联多个员工
        accountStaff.setOldStaffId(accountManagerService.selectStaffIdById(accountStaff.getAccountId()));
        if(state != 3 && (flag == 0 || flag == 3)){
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
            if(accountStaff.getOldStaffId() != null && !accountStaff.getOldStaffId().equals(accountStaff.getStaffId())){
                accountStaff.setSecretEmail("");
                accountStaff.setStaffId(accountStaff.getOldStaffId());
                accountStaff.setAccountId(0L);
                accountManagerService.updateStaffAccount(accountStaff);
            }
        }
        return Result.ok().put("state",state).put("flag",flag);
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
    public Result deleAccount(@RequestBody AccountStaff accountStaff,HttpSession session){
        int state = accountManagerService.getAccountStateById(accountStaff.getAccountId());
        if(state != 3) {
            accountStaff.setCreateEmp((Long) session.getAttribute("accountId"));
            accountStaff.setModifyEmp((Long) session.getAttribute("accountId"));
            accountManagerService.deleteAccountById(accountStaff);
        }
        return Result.ok().put("state",state);
    }

    @ResponseBody
    @RequestMapping("/resetPass")
    public Result resetPass(@RequestBody Account account, HttpSession session){
        Long OperateAccountId = (Long) session.getAttribute("accountId");
        Account account1 = accountManagerService.selectAccountById(String.valueOf(account.getId()));
//        if (account1.getaccountState())
        String content = "请点击下面的链接重置密码 http://"+ LOCALHOST + "/resetPass 如果无法点击，请复制至浏览器。";
        Result result = new Result();
        result = accountManagerService.updateAccountStatue(account.getId(),OperateAccountId);
        AccountStaff accountStaff = new AccountStaff();
        if (account1.getSecretEmail() == null || account1.getSecretEmail().equals("")) {
            result.put("code", 202);
        } else {
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
        }
        return result;
    }
    /**
     * description: 根据账户id查询员工id
     * @author junqiang.zhang@ucarinc.com
     * @date: 2019/8/20
     * @params: accountId 账户id
     * @return: int 账户状态
     */
    @ResponseBody
    @RequestMapping(value = "getAccountState.do_",method = RequestMethod.POST)
    public Result getAccountStateById(@RequestBody String AccountId) {
        int state = accountManagerService.getAccountStateById(Long.parseLong(AccountId));
        return Result.ok().put("state",state);
    }
}
