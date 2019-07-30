package com.ucar.combination.service.impl;

import com.ucar.combination.dao.DepartmentDao;
import com.ucar.combination.model.Department;
import com.ucar.combination.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Department> queryListAll() {
        return departmentDao.queryListAll();
    }

}
