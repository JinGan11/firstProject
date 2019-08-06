package com.ucar.combination.dao;

import com.ucar.combination.model.dto.RoleDto;

import java.util.List;
import java.util.Map;

/**
 * Description: 角色管理
 *
 * @author gan.jin@ucarinc.com
 * @version 1.0
 * @Date 2019/7/31 16:57
 */
public interface RoleManagementDao {
    List<Map<String, Object>> queryList(Map<String, Object> map);
    void updateStatus(int id);
    void insertRole(RoleDto role);
}
