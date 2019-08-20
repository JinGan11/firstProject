package com.ucar.combination.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.Department;
import com.ucar.combination.model.dto.*;

import java.util.List;
import java.util.Map;

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
    Map<String,Object> deleteDepartment(Long departmentId);

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
    Map<String,Object> updateUpperDepartment(Long id, String upperDepartmentNo);

    /**
     * description: 获取支持的业务线
     *
     * @return
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/8 14:21
     * @params
     */
    String selectSupportBusiness(String departmentNo);

    /**
     * description: 根据id查找部门详情，用于修改
     *
     * @return
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/9 11:20
     * @params
     */
    DepartmentEditDto selectDepartmentForEdit(Long id);

    /**
     * description: 修改部门信息
     *
     * @return
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/9 19:03
     * @params
     */
    Boolean updateDepartment(Department department);

    /**
     * description: 获取可操作的部门（建议权限为[全部]时不要调用，效率低）
     *
     * @return 存放部门id的list
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/10 16:31
     * @params account_id
     */
    List<Long> selectDataPowerIds(Long accountId);

    /**
     * description: 校验
     *
     * @return result(true, false), msg(错误的时候返回错误信息)
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/12 10:30
     * @params
     */
    Map<String, Object> checkInput(Department department);

    /**
     * description: 修改上级部门时判断是否工作点相同
     *
     * @return
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/12 15:25
     * @params id，上级部门编号
     */
    Map<String, Object> checkWorkplaceForUpper(Long id, String upperDepartment);

    /**
     * description: 建立部门树用于其他模块选择
     *
     * @return
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/12 17:09
     * @params
     */
    DepartmentTree2Dto buildTree2();

    ResultPage searchDepartment(QueryParam queryParam);

    /**
     * description:
     *
     * @return 得到部门信息
     * @author jing.luo01@ucarinc.com
     * @date 2019/8/6 10:11
     * @params id  部门ID
     */
    DepartmentDto getDepartmentDtoById(String id);

    /**
     * description: 得到SearchDepartmentDto的所有信息
     *
     * @return 返回SearchDepartmentDto的集合
     * @author jing.luo01@ucarinc.com
     * @date 2019/8/15 9:36
     * @params
     */
    List<SearchDepartmentDto> searchDepartmentAll(QueryParam queryParam);


    /**
     * description: 确认所有部门是否有效
     *
     * @return true：所有部门均有效，false：有一个或以上部门无效
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/17 16:50
     * @params 多个部门id或多个部门编号，没有的传null
     */
    Boolean checkStatusChange(List<Long> departmentId, List<String> departmentNo);

    /**
     * description: 确认所有部门是否有效
     *
     * @return true：所有部门均有效，false：有一个或以上部门无效
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/17 16:50
     * @params 部门id或部门编号，没有的传null
     */
    Boolean checkStatusChange(Long departmentId, String departmentNo);

    /**
     * description: 查询下级部门支持的业务线
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/20 10:29
     * @params
     * @return
     */
    String getLowerSupports(String no);

}
