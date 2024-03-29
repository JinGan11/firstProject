package com.ucar.combination.controller;

import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.AssignPermission;
import com.ucar.combination.model.Role;
import com.ucar.combination.model.RolePower;
import com.ucar.combination.model.dto.RoleAccountStaffDto;
import com.ucar.combination.model.*;
import com.ucar.combination.model.dto.RoleDto;
import com.ucar.combination.service.AccountManagerService;
import com.ucar.combination.service.impl.RoleManagementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ucar.combination.common.CommonMethod.objectToMap;

/**
 * Description: 角色管理
 *
 * @author gan.jin@ucarinc.com
 * @version 1.0
 * @Date 2019/7/31 16:47
 */
@Controller
@RequestMapping("/roleManage")
public class RoleManagementController {

    @Autowired(required = false)
    private RoleManagementServiceImpl roleManagementService;

    @Autowired
    private AccountManagerService accountManagerService;

	/**
	 * description:用于接收所有数据返回前端列表
	 *
	 * @param request 前端请求
	 * @return com.ucar.combination.common.Result 返回数据
	 * @author gan.jin@ucarinc.com
	 * @date 2019/8/7 15:18
	 */

	@ResponseBody
	@RequestMapping("/querylist.do_")
	public Result list(HttpServletRequest request) {
		String page = request.getParameter("page");
		String limit = request.getParameter("limit");
		String roleName = request.getParameter("roleName");
		String flag = request.getParameter("flag");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("limit", limit);
		params.put("role_name", roleName);
		params.put("flag",flag);
		ResultPage resultPage = roleManagementService.queryList(new QueryParam(params));
		List<Role> roleList = roleManagementService.queryroleList(new QueryParam(params));
		return Result.ok().put("page", resultPage).put("RoleStatusEnum", CommonEnums.toEnumMap(CommonEnums.RoleStatusEnum.values())).put("roleList",roleList);
	}



    /**
     * description:用于逻辑删除数据
     *
     * @param request 前端请求
     * @return void
     * @author gan.jin@ucarinc.com
     * @date 2019/8/7 15:20
     */

    @ResponseBody
    @RequestMapping("/updateStatus.do_")
    public Result update(HttpServletRequest request, HttpSession session) {
        String strid = request.getParameter("selection");
        int id = Integer.parseInt(strid);
        if (roleManagementService.getOneInf(id).getRoleStatus() == 0) {
            return Result.ok().put("msg", "角色删除失败，角色已经被删除");
        } else {
            Long accountId = (Long) session.getAttribute("accountId");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", id);
            params.put("accountId", accountId);
            roleManagementService.updateStatus(params);
            return Result.ok().put("msg", "1");
        }
    }

    /**
     * description:判断角色名称是否存在
     *
     * @param request
     * @return com.ucar.combination.common.Result
     * @author gan.jin@ucarinc.com
     * @date 2019/8/12 11:04
     */

    @ResponseBody
    @RequestMapping("/judgeExist.do_")
    public Result judge(HttpServletRequest request) {
        String roleName = request.getParameter("roleName");
        Role result = new Role();
        result = roleManagementService.judgeExist(roleName);
        if (result == null) {
            Role result1 = new Role();
            result1.setRoleId(0);
            return Result.ok().put("page", result1);
        }
        return Result.ok().put("page", result);
    }

    /**
     * description: 用于插入数据
     *
     * @param role 前端请求传入对象role
     * @return void
     * @author gan.jin@ucarinc.com
     * @date 2019/8/7 15:21
     */

    @ResponseBody
    @RequestMapping(value = "/insertRole.do_", method = RequestMethod.POST)
    public Result insertRole(@RequestBody RoleDto role, HttpSession session) {
        Account account = accountManagerService.selectAccountByNum(role.getAccountNum());
        if (account.getaccountState() == 3) {
            return Result.ok().put("msg", "该账户已被删除，不可选择");
        } else {
            Long accountId = (Long) session.getAttribute("accountId");
            role.setCreateEmp(accountId);
            role.setModifyEmp(accountId);
            roleManagementService.insertRole(role);
            return Result.ok().put("msg", "1");
        }
    }

    /**
     * description:将选中的数据返回到修改页面
     *
     * @param request 前端请求
     * @return com.ucar.combination.common.Result 处理结果
     * @author gan.jin@ucarinc.com
     * @date 2019/8/7 15:22
     */

    @ResponseBody
    @RequestMapping("/getOneInf.do_")
    public Result getOneInf(HttpServletRequest request) {
        String strid = request.getParameter("roleID");
        int id = Integer.parseInt(strid);
        RoleDto roleDto = roleManagementService.getOneInf(id);
        roleDto.setBusinessLine(roleDto.getBusinessLine().replace(',', ';'));
        //System.out.println("ASDFA");
        return Result.ok().put("page", roleDto).put("RoleStatusEnum", CommonEnums.toEnumMap(CommonEnums.RoleStatusEnum.values()));
    }

    @ResponseBody
    @RequestMapping("/getOtherOneInf.do_")
    public Result getOtherOneInf(HttpServletRequest request) {
        String strid = request.getParameter("roleID");
        int id = Integer.parseInt(strid);
        RoleAccountStaffDto roleAccountStaffDto = roleManagementService.getOtherOneInf(id);
        RoleAccountStaffDto roleAccountStaffDto1 = roleManagementService.getOtherOneInf1(id);
        roleAccountStaffDto.setModifyEmp(roleAccountStaffDto1.getModifyEmp());
        roleAccountStaffDto.setModifyEmpName(roleAccountStaffDto1.getModifyEmpName());
        roleAccountStaffDto.setModifyEmpNum(roleAccountStaffDto1.getModifyEmpNum());
        roleAccountStaffDto.setModifyTime(roleAccountStaffDto1.getModifyTime());
        //System.out.println("ASDFA");
        return Result.ok().put("page", roleAccountStaffDto);
    }

    /**
     * description:修改后的数据插入
     *
     * @param role 传入对象
     * @return void
     * @author gan.jin@ucarinc.com
     * @date 2019/8/7 15:23
     */

    @ResponseBody
    @RequestMapping(value = "/updateByModify.do_", method = RequestMethod.POST)
    public Result updateByModify(@RequestBody RoleDto role, HttpSession session) {
        Account account = accountManagerService.selectAccountByNum(role.getAccountNum());
        if (account.getaccountState() == 3) {
            return Result.ok().put("msg", "该账户已被删除，不可选择");
        } else if (roleManagementService.getOneInf(Integer.parseInt(String.valueOf(role.getRoleID()))).getRoleStatus() == 0) {
            return Result.ok().put("msg", "该角色已被删除，不可修改");
        } else {
            Long accountId = (Long) session.getAttribute("accountId");
            role.setModifyEmp(accountId);
            roleManagementService.updateByModify(role);
            return Result.ok().put("msg", "1");
        }
    }

    /**
     * description: 为角色分配权限
     *
     * @return
     * @author peng.zhang11@ucarinc.com
     * @date 2019/8/5 22:51
     * @params
     */
    @ResponseBody
    @RequestMapping("/assignPermission")
    public Result assignPermission(@RequestBody AssignPermission assignPermission, HttpSession session) {
        Long accountId = (Long) session.getAttribute("accountId");
        RoleDto roleDto1 = roleManagementService.getOneInf(Math.toIntExact(assignPermission.getRoleInfoId()));
        if (roleDto1.getRoleStatus() == 0) {
            return Result.ok().put("code", 203)
                    .put("msg", "保存失败，该角色已失效");
        }
        Result result = roleManagementService.assignPermission(assignPermission, accountId);
        RoleDto roleDto = roleManagementService.getOneInf(Math.toIntExact(assignPermission.getRoleInfoId()));
        roleDto.setModifyEmp(accountId);
        roleManagementService.updateByModify(roleDto);
        Account account = accountManagerService.selectAccountById(String.valueOf(accountId));
        AccountStaff accountStaff = new AccountStaff();
        if (account != null) {
            accountStaff.setAccountId(account.getId());
            accountStaff.setOperationType("角色分配权限");
            accountStaff.setStaffId(account.getStaffId());
            accountStaff.setAccountState(account.getaccountState());
            accountStaff.setPermissions(account.getPremissions());
            accountStaff.setStaffNum(account.getStaffNum());
            accountStaff.setStaffName(account.getStaffName());
            accountStaff.setCreateEmp(accountId);
            accountManagerService.insertAccountHistory(accountStaff);
        }
        return result;
    }

    /**
     * description: 获取角色已拥有的权限
     *
     * @return
     * @author peng.zhang11@ucarinc.com
     * @date 2019/8/7 14:49
     * @params
     */
    @ResponseBody
    @RequestMapping("/getRolePower.do_")
    public Result getRolePower(@RequestBody RolePower rolePower, HttpSession session) {
        return roleManagementService.getRolePower(rolePower);
    }

    /**
     * description:
     *
     * @param request
     * @return
     * @author qingyu.lan@ucarinc.com
     * @date 2019/8/12 13:17
     */
    @ResponseBody
    @RequestMapping("/getRoleAccountList.do_")
    public Result getRoleAccountList(HttpServletRequest request) {
        String roleId = request.getParameter("roleId");
        String accountId = request.getParameter("accountName");
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        params.put("limit", limit);
        params.put("roleId", roleId);
        params.put("accountName", accountId);
        ResultPage resultPage = roleManagementService.getRoleAccountList(new QueryParam(params));
        return new Result().ok().put("page", resultPage).put("permissionEnum", CommonEnums.toEnumMap(CommonEnums.Permission.values()))
                .put("accountStatusEnum", CommonEnums.toEnumMap(CommonEnums.AccountStatusEnum.values()));
    }

    /**
     * description:删除角色关联账号
     *
     * @param request
     * @return
     * @author qingyu.lan@ucarinc.com
     * @date 2019/8/12 13:17
     */
    @ResponseBody
    @RequestMapping("/removeRoleAccount.do_")
    public Result removeAddAccount(HttpServletRequest request) {
        String roleId = request.getParameter("roleId");
        String ids = request.getParameter("accountIds");
        String list = request.getParameter("accountNameList");
        String[] accountIds = ids.split(",");
        String[] accountNameList = list.split(",");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("roleId", roleId);
        params.put("accountIds", accountIds);
        params.put("accountNameList", accountNameList);
        List<String> romoveAccounts = roleManagementService.removeRoleAccount(params);
        return new Result().ok().put("msg", "成功删除").put("romoveAccounts", romoveAccounts);
    }

    /**
     * description:添加角色关联账号
     *
     * @param request
     * @return
     * @author qingyu.lan@ucarinc.com
     * @date 2019/8/12 15:17
     */
    @ResponseBody
    @RequestMapping("/addRoleAccount.do_")
    public Result addRoleAccount(HttpServletRequest request, HttpSession session) {
        String roleId = request.getParameter("roleId");
        String ids = request.getParameter("accountIds");
        String names =request.getParameter("accountNames");
        String[] accountIds = ids.split(",");
        String[] accountNames = names.split(",");
        Long createId = (Long) session.getAttribute("accountId");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("roleId", roleId);
        params.put("accountIds", accountIds);
        params.put("accountNames", accountNames);
        params.put("createEmp", createId);
        params.put("modifyEmp", createId);
        String msg = roleManagementService.addRoleAccount(params);
        return new Result().ok().put("msg", msg);
    }
}
