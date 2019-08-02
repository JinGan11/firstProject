package com.ucar.combination.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.DepartmentDao;
import com.ucar.combination.model.Department;
import com.ucar.combination.model.dto.SearchDepartmentDto;
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

	/*
	 * description:
	 * @author jing.luo01@ucarinc.com
	 * @date   2019/8/2 11:42
	 * @params queryParam 输入查询分页的QueryParam
	 * @return 返回查询部门
	 */
	@Override
	public ResultPage searchDepartment(QueryParam queryParam) {
		Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
		List<SearchDepartmentDto> list = departmentDao.searchDepartment(queryParam);
		return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
	}
}
