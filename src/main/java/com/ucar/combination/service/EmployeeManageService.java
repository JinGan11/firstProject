package com.ucar.combination.service;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.Staff;
import com.ucar.combination.model.dto.StaffAccountDTO;

import java.util.List;
import java.util.Map;


/**
 * description:员工管理
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-27 17:23:44
 **/
public interface EmployeeManageService {
    /**
     * 员工管理列表
     * @param queryParam
     * @return
     */
    ResultPage queryList(QueryParam queryParam);

    /**
     * 导出员工列表
     * @param queryParam
     * @return
     */
    public List<Object> getStaffList(QueryParam queryParam);
    /**
     * 插入新员工
     * @param staff
     * @return
     */
    public void insertStaff(Staff staff);
    /**
     * description:由员工id将员工状态设置为无效
     * @author shiya.li@ucarinc.com
     * @date   2019/8/6 17：16
     * @params id 员工id
     * @return 无
     */
    public void updateStatus(int id);
    /**
     * description: 更新员工离职
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/8 9:48
     * @params
     * @return
     */
    public int updateDimission(String id);

    /**
     * description: 按ID选择员工信息
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/8 10:09
     * @params 员工ID
     * @return 员工对象
     */
    public Staff selectById(String id);

    /**
     * description: 员工恢复
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/8 14:10
     * @params 员工ID
     * @return 更新的数据
     */
    public int updateDimissionRecovery(String id);
    /**
     * description:修改员工信息
     * @author shiya.li@ucarinc.com
     * @date   2019/8/8 15：11
     * @params
     * @return
     */
    public void updateStaff(Staff staff);
    /**
     * description: 查看所有的员工编号
     * @author shiya.li@ucarinc.com
     * @date 2019/8/14 17:20
     * @params
     * @return
     */
    public List<String> selectAllStaffNum();

    /**
     * description: 获取创建人和修改人值
     * @author: jianan.shu@ucarinc.com
     * @param: id 员工id
     * @date: 2019/8/14 16:34
     * @return：
     */
    public StaffAccountDTO getInfoByStaffId(long id);

    /**
     * description: 分配部门
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/17 3:01
     * @params employeeMap 传入HASHMAP里面有ID,DEPARTMETNID

     * @return 状态
     */
    public Integer updateDepartmentByEmployee(Map<String,String> employeeMap);

   //lzy
    Staff getStaffInfById(Long staffId);
}
