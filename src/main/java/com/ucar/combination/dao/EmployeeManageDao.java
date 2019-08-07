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
    /**
     * description:在员工表中插入新员工
     * @author shiya.li@ucarinc.com
     * @date   2019/8/5 9:42
     * @params staff 员工对象
     * @return 无
     */
    void insertStaff(Staff staff);
    /**
     * description:由员工id将员工状态设置为无效
     * @author shiya.li@ucarinc.com
     * @date   2019/8/6 17：16
     * @params id 员工id
     * @return 无
     */
    void updateStatus(int id);
}
