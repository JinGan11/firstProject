package com.ucar.combination.dao;

import com.ucar.combination.model.Department;
import com.ucar.combination.model.dto.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @author 郑开添（kaitian.zheng@ucarinc.com）
 * @version 1.0
 * @date 2019/7/30 10:20
 **/
@Repository(value = "DepartmentDao")
public interface DepartmentDao {

    /**
     * description: 查找所有部门
     *
     * @return 存放所有有效部门的DepartmentDto的list
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/6 17:05
     * @params
     */
    List<DepartmentTreeDto> queryDepartmentTreeAll();

    /**
     * description: 插入一个新的部门，创建/修改时间为当前时间
     *
     * @return
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/6 17:05
     * @params
     */
    void insertDepartment(Department department);

    /**
     * description: 删除部门（逻辑删除）
     *
     * @return
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/6 17:06
     * @params
     */
    void deleteDepartment(Long id);

    /**
     * description: 查询所有部门，用于修改上级部门
     *
     * @return
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/8 8:53
     * @params
     */
    List<DepartmentUpperDto> queryDepartmentUpperAll();

    /**
     * description: 修改上级部门
     *
     * @return
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/8 10:57
     * @params
     */
    void updateUpperDepartment(Long id,String upperDepartmentNo);

    /**
     * description: 根据部门编号查询支持的业务线
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/8 14:51
     * @params
     * @return
     */
    String selectSupportBusinessByNo(String departmentNo);

    /**
     * description: 根据id查department用于修改
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/9 10:58
     * @params
     * @return
     */
    DepartmentEditDto selectDepartmentForEdit(Long id);

    /*
     * description:
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/2 11:46
     * @params
     * @return 查询出来的SearchDepartmentDto结果，在
     */
    List<SearchDepartmentDto> searchDepartment(Map<String, Object> map);

    /*
     * description:
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/5 16:53
     * @params id 传入一个LONG型的ID进行查找
     * @return Department返回一个部门类。
     */
    DepartmentDto getDepartmentDtoById(Long id);
}
