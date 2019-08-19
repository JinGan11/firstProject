package com.ucar.combination.dao;

import com.ucar.combination.model.Department;
import com.ucar.combination.model.dto.*;
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

    /**
     * description: 修改部门
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/9 19:04
     * @params
     * @return
     */
    void updateDepartment(Department department);

    /**
     * description: 获取可操作的部门的id，数据权限类型为5-手动选择时使用
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/10 16:47
     * @params
     * @return
     */
    List<Long> selectDataPowerChoosed(Long account_id);

    /**
     * description: 查找全部部门id
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/12 9:46
     * @params
     * @return
     */
    List<Long> selectDepartmentIdAll();

    /**
     * description: 校验部门编号
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/12 10:50
     * @params
     * @return
     */
    Integer checkDepartmentNo(Department department);

    /**
     * description: 校验办公点标识
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/12 11:10
     * @params
     * @return
     */
    Integer checkWorkplace(Department department);

    /**
     * description: 校验上级部门
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/12 14:30
     * @params
     * @return
     */
    Integer checkUpperDepartment(Department department);

    /**
     * description: 校验下级部门
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/12 14:42
     * @params
     * @return
     */
    Integer checkLowerDepartment(Department department);

    /**
     * description: 修改上级部门时校验办公点
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/12 15:34
     * @params
     * @return
     */
    Integer checkWorkplaceForUpper(Long id,String upperDepartmentNo);

    /**
     * description: 根据id查找级别
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/12 15:37
     * @params
     * @return
     */
    Integer selectLevel(Long id);

    List<DepartmentTree2Dto> selectDepartmentTree2Dto();

    /**
     * description: 查找下级部门支持的业务线
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/13 15:19
     * @params
     * @return
     */
    List<String> selectLowerDepartmentBusiness(String departmentNo);

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

    /**
     * description: 校验有效性是否更改
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/17 16:06
     * @params
     * @return 返回值大于0：成功
     */
    Integer checkStatusById(Long departmentId);

    /**
     * description: 校验有效性是否更改
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/17 16:06
     * @params
     * @return 返回值大于0：成功
     */
    Integer checkStatusByNo(String departmentNo);

    /**
     * description: 获取上级部门业务线
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/19 14:02
     * @params
     * @return
     */
    String selectUpperDepartmentBusiness(String upperDepartmentNo);

    /**
     * description: 检查下级部门是否有有效的
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/19 14:45
     * @params
     * @return
     */
    Integer checkLowerDepartmentsStatus(String departmentNo);

    /**
     * description: 查找该部门的上级部门
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/19 15:22
     * @params
     * @return
     */
    String selectUpperDepartmentNo(Long id);

    /**
     * description: 获取部门编号
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/19 14:48
     * @params 部门id
     * @return
     */
    String selectDepartmentNoById(Long id);

    String selectEmployeeNameByAccountId(Long accountId);
}
