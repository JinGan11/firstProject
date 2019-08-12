package com.ucar.combination.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.Department;
import com.ucar.combination.model.dto.DepartmentDto;
import com.ucar.combination.model.dto.DepartmentEditDto;
import com.ucar.combination.model.dto.DepartmentTreeDto;
import com.ucar.combination.model.dto.DepartmentUpperDto;

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

    /**
     * description: 根据id查找部门详情，用于修改
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/9 11:20
     * @params
     * @return
     */
    DepartmentEditDto selectDepartmentForEdit(Long id);

    /**
     * description: 修改部门信息
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/9 19:03
     * @params
     * @return
     */
    Boolean updateDepartment(Department department);

    /**
     * description: 获取可操作的部门（建议权限为[全部]时不要调用，效率低）
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/10 16:31
     * @params account_id
     * @return 存放部门id的list
     */
    List<Long> selectDataPowerIds(Long accountId);






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
