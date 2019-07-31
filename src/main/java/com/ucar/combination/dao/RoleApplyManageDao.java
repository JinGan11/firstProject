package com.ucar.combination.dao;

import java.util.List;
import java.util.Map;

public interface RoleApplyManageDao {
    /**
     * 角色申请管理列表
     * @param map
     * @return
     */
    List<Map<String, Object>> queryList(Map<String, Object> map);
}
