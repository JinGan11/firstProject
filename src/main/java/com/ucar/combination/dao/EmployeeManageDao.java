package com.ucar.combination.dao;

import com.ucar.combination.model.AccountStaff;
import com.ucar.combination.model.dto.StaffAccountDTO;
import com.ucar.combination.model.dto.StaffDto;

import com.ucar.combination.model.Staff;
import com.ucar.combination.model.User;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;

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

    /*
     * description: 修改员工的账户信息
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date： 2019/8/9 13:04
     * @params accountStaff 要修改的信息
     * @return
     */
    int updateAccount(AccountStaff accountStaff);

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
    /**
     * description: 更新员工离职新信息
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/8 9:49
     * @params 员工ID
     * @return 状态
     */
    int updateDimission(String id);

    /**
     * description: 按ID选择员工信息
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/8 10:11
     * @params 员工ID
     * @return 员工信息
     */
    Staff selectById(String id);
    /**
     * description: 员工恢复在职
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/8 14:12
     * @params id 员工ID
     * @return 更新的多少条数据
     */
    int updateDimissionRecovery(String id);
    /**
     * description:修改员工信息
     * @author shiya.li@ucarinc.com
     * @date   2019/8/8 15：11
     * @params
     * @return
     */
    void updateStaff(Staff staff);


    /**
     * description: 查看员工所属部门
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/12 9:06
     * @params account_id（账号id）
     * @return
     */
    Long selectDepartmentId(Long id);
    /**
     * description: 查看所有的员工编号
     * @author shiya.li@ucarinc.com
     * @date 2019/8/14 17:20
     * @params
     * @return
     */
    List<String> selectAllStaffName();
    /**
     * description: 获取员工修改人和创建人
     * @author: jianan.shu@ucarinc.com
     * @param:
     * @date: 2019/8/14 16:43
     * @return：
     */
    Map<String,Object>getCreateInfo(long staffId);
    Map<String,Object> getModifyInfo(long staffId);

    Long getCreateStaffId(@Param("staffId")long id);
    Long getModifyStaffId(@Param("staffId")long id);
    String getCreateEmpById(long accountId);
    String getModifyEmpById(long accountId);

    /**
     * description: 分配部门
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/17 3:03
     * @params employeeMap 描述：MAP中存储着ID,DEPARTMENTID

     * @return 状态
     */
    Integer updateDepartmentByEmployee(Map<String,String> employeeMap);

}
