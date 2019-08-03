package com.ucar.combination.dao;

import com.ucar.combination.model.dto.StaffDto;

import com.ucar.combination.model.Staff;
import com.ucar.combination.model.User;

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
    List<Object> queryList(Map<String, Object> map);

    /**
     * description: 通过id查询员工信息
     * @author peng.zhang11@ucarinc.com
     * @date   2019/7/31 16:42
     * @params user 账户中的员工ID
     * @return 员工信息
     */
    List<Staff> qryStaffById(User user);
}
