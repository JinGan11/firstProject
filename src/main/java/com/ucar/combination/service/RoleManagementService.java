package com.ucar.combination.service;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.Account;
import com.ucar.combination.model.AssignPermission;
import com.ucar.combination.model.PowerList;
import com.ucar.combination.model.Role;
import com.ucar.combination.model.RolePower;
import com.ucar.combination.model.dto.RoleAccountStaffDto;
import com.ucar.combination.model.dto.RoleDto;

import java.util.List;
import java.util.Map;

/**
 * Description: 角色管理
 *
 * @author gan.jin@ucarinc.com
 * @version 1.0
 * @Date 2019/7/31 16:52
 */
public interface RoleManagementService {

    ResultPage queryList(QueryParam queryParam);
    ResultPage getRoleList();
    void updateStatus(int id);
    void insertRole(RoleDto role);
    Role judgeExist(String roleName);
    ResultPage getOwnedRoleList(Account account);
    ResultPage getnotOwnedRoleList(Account account);
    RoleDto getOneInf(int id);
    RoleAccountStaffDto getOtherOneInf(int id);
    RoleAccountStaffDto getOtherOneInf1(int id);
    Result assignPermission(AssignPermission assignPermission, Long accountId);
    Result getRolePower(RolePower rolePower);
    void updateByModify(RoleDto role);
    List<RolePower> getRolePowerList(Long roleInfoId);
    List<Role> queryroleList();

    /**
     * description: 获取角色账户信息
     * @author qingyu.lan@ucarinc.com
     * @date   2019/8/12 11:06
     * @return
     */
    ResultPage getRoleAccountList(QueryParam queryParam);

    /**
     * description: 删除角色账户信息
     * @author qingyu.lan@ucarinc.com
     * @date   2019/8/12 13:06
     * @param map
     */
    List<String> removeRoleAccount(Map<String, Object> map);

    /**
     * description: 添加角色账户信息
     * @author qingyu.lan@ucarinc.com
     * @date   2019/8/12 13:06
     * @param map
     */
    String addRoleAccount(Map<String, Object> map);
}
