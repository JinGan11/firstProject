package com.ucar.combination.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.AccountManageDao;
import com.ucar.combination.dao.DepartmentDao;
import com.ucar.combination.dao.EmployeeManageDao;
import com.ucar.combination.model.Department;
import com.ucar.combination.model.dto.*;
import com.ucar.combination.service.DepartmentService;
import com.ucar.combination.utils.DepartmentTreeBuilder;
import com.ucar.combination.utils.DepartmentUpperTreeBuilder;
import com.ucar.combination.utils.SupportBusinessUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:部门管理
 *
 * @author 郑开添（kaitian.zheng@ucarinc.com）
 * @version 1.0
 * @date 2019/7/30 10:16
 **/
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;
    @Autowired(required = false)
    private AccountManageDao accountManageDao;
    @Autowired(required = false)
    private EmployeeManageDao employeeManageDao;

    @Override
    public DepartmentTreeDto buildTree() {
        List<DepartmentTreeDto> list = departmentDao.queryDepartmentTreeAll();
        return new DepartmentTreeBuilder().buildTree(list);
    }

    @Override
    public void insertDepartment(Department department) {
        departmentDao.insertDepartment(department);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        departmentDao.deleteDepartment(departmentId);
    }

    @Override
    public DepartmentUpperDto buildUpperTree(Long choosedId) {
        List<DepartmentUpperDto> list = departmentDao.queryDepartmentUpperAll();
        list = SupportBusinessUtil.setCanChooseBySupports(list, choosedId);
        DepartmentUpperDto rootNode = new DepartmentUpperTreeBuilder().buildTree(list, choosedId);
        return rootNode;
    }

    @Override
    public Boolean updateUpperDepartment(Long id, String upperDepartmentNo) {
        departmentDao.updateUpperDepartment(id, upperDepartmentNo);
        return null;
    }

    @Override
    public String selectSupportBusiness(String departmentNo) {
        return departmentDao.selectSupportBusinessByNo(departmentNo);
    }

    @Override
    public DepartmentEditDto selectDepartmentForEdit(Long id) {
        return departmentDao.selectDepartmentForEdit(id);
    }

    @Override
    public List<Long> selectDataPowerIds(Long accountId) {
        // type:1-全部,2-递归,3-本部门,4-本人,5-手动选择
        Integer powerType = accountManageDao.selectPermissionsById(accountId);
        if (powerType == null) return null;
        switch (powerType) {
            case 1: // 1-全部（不建议在数据权限为1-全部时使用，效率低）
                return departmentDao.selectDepartmentIdAll();
            case 2: // 2-递归
                DepartmentTreeBuilder builder = new DepartmentTreeBuilder();
                List<DepartmentTreeDto> list = departmentDao.queryDepartmentTreeAll();
                builder.buildTree(list);
                List<Long> ids2 = builder.getRecursionId(accountId);
                return ids2;
            case 3: //3-本部门
                List<Long> ids3 = new ArrayList<>();
                ids3.add(employeeManageDao.selectDepartmentId(accountId));
                return ids3;
            case 4: // 4-本人（相当于没有权限，但是应该可以操作自己？）
                return new ArrayList<Long>();
            case 5: // 5-手动选择
                return departmentDao.selectDataPowerChoosed(accountId);
            default:
                return null;
        }
    }

    @Override
    public Map<String, Object> checkInput(Department department) {
        Map<String, Object> map = new HashMap<>();
        map.put("result", true);
        if (departmentDao.checkDepartmentNo(department) > 0) {
            map.put("result", false);
            map.put("msg", "部门编号已存在！");
        }
        if (department.getLevel().equals(5)) {
            if (department.getWorkplace().equals(0)) {
                map.put("result", false);
                String tmp = (String) map.get("msg") == null ? "" : (String) map.get("msg");
                map.put("msg", tmp + "办公点标识不能设置为0！");
            } else if (departmentDao.checkWorkplace(department) > 0) {
                map.put("result", false);
                String tmp = (String) map.get("msg") == null ? "" : (String) map.get("msg");
                map.put("msg", tmp + "办公点标识已存在！");
            }
        }
        if (departmentDao.checkUpperDepartment(department) < 1) {
            map.put("result", false);
            String tmp = (String) map.get("msg") == null ? "" : (String) map.get("msg");
            map.put("msg", tmp + "上级部门状态为无效，无法将当前部门改为有效！");
        }
        if (department.getStatus().equals(0) && (departmentDao.checkLowerDepartment(department) > 0)) {
            map.put("result", false);
            String tmp = (String) map.get("msg") == null ? "" : (String) map.get("msg");
            map.put("msg", tmp + "下级部门包含状态为有效的部门，无法将当前部门改为无效！");
        }
        return map;
    }

    @Override
    public Map<String, Object> checkWorkplaceForUpper(Long id, String upperDepartment) {
        Map<String, Object> map = new HashMap<>();
        map.put("result", true);
        if (departmentDao.selectLevel(id).equals(5)) {
            if(departmentDao.checkWorkplaceForUpper(id,upperDepartment)>0){
                map.put("result", false);
                map.put("msg", "该上级部门中已有办公点标识与本部门相同的部门，请修改办公点标识后再进行修改！");
            }
        }
        return map;
    }

    /*
     * description:
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/2 11:42
     * @params queryParam 输入查询分页的QueryParam
     * @return 返回查询部门
     */
    @Override
    public ResultPage searchDepartment(QueryParam queryParam) {
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<SearchDepartmentDto> list = departmentDao.searchDepartment(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }

    public Boolean updateDepartment(Department department) {
        departmentDao.updateDepartment(department);
        return true;
    }

    /*
     * description: 访问DAO层得到DEPARTMENT数据库的信息
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/6 10:13
     * @params id 描述

     * @return
     */
    @Override
    public DepartmentDto getDepartmentDtoById(String id) {
        return departmentDao.getDepartmentDtoById(Long.valueOf(id));
    }
}
