package com.ucar.combination.dao;

import com.ucar.combination.model.Department;
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

}
