package com.ucar.combination.service;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.dto.RoleDto;

/**
 * Description: 角色管理
 *
 * @author gan.jin@ucarinc.com
 * @version 1.0
 * @Date 2019/7/31 16:52
 */
public interface RoleManagementService {

    ResultPage queryList(QueryParam queryParam);
    void updateStatus(int id);
    void insertRole(RoleDto role);
    RoleDto getOneInf(int id);
}
