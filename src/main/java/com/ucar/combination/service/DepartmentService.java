package com.ucar.combination.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.Department;
import com.ucar.combination.model.dto.DepartmentDto;
import com.ucar.combination.model.dto.DepartmentTreeDto;
import com.ucar.combination.model.dto.DepartmentUpperDto;

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
     *
     * @return 树的根节点
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/5 13:45
     * @params
     */
    DepartmentTreeDto buildTree();

    /**
     * description: 插入新部门
     *
     * @return
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/5 19:27
     * @params 不包含id在内的department所有属性
     */
    void insertDepartment(Department department);

    /**
     * description: 删除部门（逻辑删除）
     *
     * @return
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/6 16:59
     * @params
     */
    void deleteDepartment(Long departmentId);

    /**
     * description: 修改上级部门使用的树结构部门
     *
     * @return
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/8 8:50
     * @params
     */
    DepartmentUpperDto buildUpperTree(Long choosedId);

    /**
     * description: 修改上级节点
     *
     * @return 修改是否成功
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/8 10:50
     * @params
     */
    Boolean updateUpperDepartment(Long id, String upperDepartmentNo);

    /**
     * description: 获取支持的业务线
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/8 14:21
     * @params
     * @return
     */
    String selectSupportBusiness(String departmentNo);

    ResultPage searchDepartment(QueryParam queryParam);

    /*
     * description:
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/6 10:11
     * @params id  部门ID

     * @return 得到部门信息
     */
    DepartmentDto getDepartmentDtoById(String id);

}
