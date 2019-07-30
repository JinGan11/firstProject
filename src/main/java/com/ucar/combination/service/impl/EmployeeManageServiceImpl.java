package com.ucar.combination.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.EmployeeManageDao;
import com.ucar.combination.service.EmployeeManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * description:员工管理列表
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-27 17:24:53
 **/
@Service
public class EmployeeManageServiceImpl implements EmployeeManageService {
    @Autowired
    private EmployeeManageDao employeeManageDao;

    @Override
    public ResultPage queryList(QueryParam queryParam) {
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<Map<String, Object>> list = employeeManageDao.queryList(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }
}
