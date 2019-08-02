package com.ucar.combination.dao;

import com.ucar.combination.model.Department;
import com.ucar.combination.model.dto.SearchDepartmentDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description:
 *
 * @author 郑开添（kaitian.zheng@ucarinc.com）
 * @version 1.0
 * @date 2019/7/30 10:20
 **/
@Repository(value = "DepartmentDao")
public interface DepartmentDao {

    List<Department> queryListAll();

    /*
     * description:
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/2 11:46
     * @params
     * @return 查询出来的SearchDepartmentDto结果，在
     */
    List<SearchDepartmentDto> searchDepartment();
}
