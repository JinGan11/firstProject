package com.ucar.combination.dao;

import com.ucar.combination.model.dto.StaffDto;

import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-27 17:19:26
 **/
public interface EmployeeManageDao {
    /**
     * 员工管理列表
     * @param map
     * @return
     */
    List<StaffDto> queryList(Map<String, Object> map);
}
