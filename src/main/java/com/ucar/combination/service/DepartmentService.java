package com.ucar.combination.service;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.Department;

import java.util.List;

/**
 * description:部门管理
 *
 * @author 郑开添（kaitian.zheng@ucarinc.com）
 * @version 1.0
 * @date 2019/7/30 10:07
 **/
public interface DepartmentService {

    /**
     * description: 查询所有部门
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/7/30 10:41
     * @return
     */
    List<Department> queryListAll();

    List<Department> searchChildDepartment(String departmentNo);

    ResultPage searchDepartment(QueryParam queryParam);

}
