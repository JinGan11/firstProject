package com.ucar.combination.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.EmployeeManageDao;
import com.ucar.combination.model.Staff;
import com.ucar.combination.model.dto.StaffDto;
import com.ucar.combination.service.EmployeeManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description:员工管理列表
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-27 17:24:53
 **/
@Service
public class EmployeeManageServiceImpl implements EmployeeManageService {
    @Autowired(required = false)
    private EmployeeManageDao employeeManageDao;

    /**
     * description：查询员工列表
     * @author qingyu.lan@ucarinc.com
     * @param queryParam
     * @return
     */
    @Override
    public ResultPage queryList(QueryParam queryParam) {
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<Object> list = employeeManageDao.queryList(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }

    /**
     * description：导出员工列表
     * @author qingyu.lan@ucarinc.com
     * @param queryParam
     * @return
     */
    public List<Object> getStaffList(QueryParam queryParam){
        return employeeManageDao.queryList(queryParam);
    }
    /**
     * description：插入新员工
     * @author shiya.li@ucarinc.com
     * @param staff:员工对象
     * @return
     */
     public void insertStaff(Staff staff){
         employeeManageDao.insertStaff(staff);
    }
    /**
     * description:由员工id将员工状态设置为无效
     * @author shiya.li@ucarinc.com
     * @date   2019/8/6 17：16
     * @params id 员工id
     * @return 无
     */
    public void updateStatus(int id){
        employeeManageDao.updateStatus(id);
    }
    /**
     * description: 更新员工离职
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/8 9:48
     * @params
     * @return
     */
    @Override
    public int updateDimission(String id) {
        employeeManageDao.updateDimission(id);
        return 0;
    }
    /**
     * description: 按ID选择员工信息
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/8 10:10
     * @params id 员工ID

     * @return 员工信息
     */
    @Override
    public Staff selectById(String id) {
        return employeeManageDao.selectById(id);
    }
    /**
     * description: 员工在职恢复
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/8 14:11
     * @params id员工ID
     * @return 员工操作的多少条数据
     */
    @Override
    public int updateDimissionRecovery(String id) {
        return employeeManageDao.updateDimissionRecovery(id);
    }
}

