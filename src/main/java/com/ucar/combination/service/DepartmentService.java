package com.ucar.combination.service;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.Department;
import com.ucar.combination.model.dto.DepartmentTreeDto;

/**
 * description:部门管理
 *
 * @author 郑开添（kaitian.zheng@ucarinc.com）
 * @version 1.0
 * @date 2019/7/30 10:07
 **/
public interface DepartmentService {

    /**
     * description: 构建树结构部门
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/5 13:45
     * @params
     * @return 树的根节点
     */
    DepartmentTreeDto buildTree();

    /**
     * description: 插入新部门
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/5 19:27
     * @params 不包含id在内的department所有属性
     * @return
     */
    void insertDepartment(Department department);

    ResultPage searchDepartment(QueryParam queryParam);

}
