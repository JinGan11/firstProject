package com.ucar.combination.dao;

import com.ucar.combination.model.dto.RoleDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Description: 角色管理
 *
 * @author gan.jin@ucarinc.com
 * @version 1.0
 * @Date 2019/7/31 16:57
 */
@Component
public interface RoleManagementDao {
    List<Map<String, Object>> queryList(Map<String, Object> map);
    void updateStatus(int id);
    void insertRole(RoleDto role);
    RoleDto getOneInf(int id);
}
