package com.ucar.combination.controller;

import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.Department;
import com.ucar.combination.service.DepartmentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:部门管理
 *
 * @author 郑开添（kaitian.zheng@ucarinc.com）
 * @version 1.0
 * @date 2019/7/30 10:03
 **/
@Controller
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;

	@ResponseBody
	@RequestMapping("/querylist.do_")
	public Result list(HttpServletRequest request) {
		List<Department> departmentList = departmentService.queryListAll();
		return new Result().put("departments", departmentList);
	}

	/*
	 * description:
	 * @author jing.luo01@ucarinc.com
	 * @date   2019/8/2 11:44
	 * @params request HTTPSERVLETREQUEST请求,后面的参数均为表单传送过来的数据
	 * @return 分页的结果集RESULT
	 */
	@ResponseBody
	@RequestMapping("/searchDepartment.do_")
	public Result searchDepartment(HttpServletRequest request,	@RequestParam(defaultValue = "") String page,@RequestParam(defaultValue = "") String limit,@RequestParam(defaultValue = "") String departmentName,@RequestParam(defaultValue = "") String staffName,@RequestParam(defaultValue = "") String staffId	,@RequestParam(defaultValue = "") String telePhone,@RequestParam(defaultValue = "") String cityName,
								   @RequestParam(defaultValue = "") String level,@RequestParam(defaultValue = "") String upperDepartmentNo,@RequestParam(defaultValue = "") String status,@RequestParam(defaultValue = "") String departmentType) {
		Map<String, Object> params = new HashMap<>();
		params.put("page", page);
		params.put("limit", limit);
		params.put("departmentName",departmentName);
		params.put("staffName",staffName);
		params.put("staffId",staffId);
		params.put("telePhone",telePhone);
		params.put("cityName",cityName);
		params.put("level",level);
		params.put("upperDepartmentNo",upperDepartmentNo);
		params.put("status",status);
		params.put("departmentType",departmentType);
		ResultPage resultPage = departmentService.searchDepartment(new QueryParam(params));
		return new Result().ok().put("page", resultPage);
	}
}
