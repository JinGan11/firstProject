package com.ucar.combination.service;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.AssignPermission;
import com.ucar.combination.model.PowerList;
import com.ucar.combination.model.RolePower;
import com.ucar.combination.model.dto.RoleDto;

import java.util.List;

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
    ResultPage getOwnedRoleList();
    ResultPage getnotOwnedRoleList();
    RoleDto getOneInf(int id);
    Result assignPermission(AssignPermission assignPermission, Long accountId);
    Result getRolePower(RolePower rolePower);
    void updateByModify(RoleDto role);
    List<RolePower> getRolePowerList(Long roleInfoId);
}
