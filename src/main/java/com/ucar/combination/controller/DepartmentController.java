package com.ucar.combination.controller;

import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.Department;
import com.ucar.combination.model.dto.DepartmentDto;
import com.ucar.combination.model.dto.DepartmentTreeDto;
import com.ucar.combination.service.CompanyManageService;
import com.ucar.combination.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
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
	@Autowired
	CompanyManageService companyManageService;

	/**
	 * description: 构建有效部门的树结构
	 * @author 郑开添（kaitian.zheng@ucarinc.com）
	 * @date 2019/8/6 19:18
	 * @params
	 * @return 树结构根节点
	 */
	@ResponseBody
	@RequestMapping("/buildTree.do_")
	public Result list() {
		DepartmentTreeDto rootNode = departmentService.buildTree();
		return new Result().put("departmentDto", rootNode);
	}

	/**
	 * description: 添加一个新的部门
	 * @author 郑开添（kaitian.zheng@ucarinc.com）
	 * @date 2019/8/6 19:18
	 * @params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addDepartment.do_")
	public String addDepartment(@RequestBody Department department, HttpSession session) {
		Long accountId = (Long) session.getAttribute("accountId");
		department.setCreateEmp(accountId);
		department.setModifyEmp(accountId);

		departmentService.insertDepartment(department);
		return "success";
	}

	/**
	 * description: 删除部门（逻辑删除），会导致其下面的部门无法显示
	 * @author 郑开添（kaitian.zheng@ucarinc.com）
	 * @date 2019/8/6 19:19
	 * @params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteDepartment.do_")
	public String deleteDepartment(@RequestBody Department department){
		departmentService.deleteDepartment(department.getId());
		return "success";
	}

	/*
	 * description:
	 * @author jing.luo01@ucarinc.com
	 * @date   2019/8/2 11:44
	 * @params request HTTPSERVLETREQUEST请求,后面的参数均为表单传送过来的数据，用@RequestParam注解接收数据
	 * @return 分页的结果集RESULT
	 */
	@ResponseBody
	@RequestMapping("/searchDepartment.do_")
	public Result searchDepartment(HttpServletRequest request, @RequestParam(defaultValue = "") String page, @RequestParam(defaultValue = "") String limit, @RequestParam(defaultValue = "") String departmentName, @RequestParam(defaultValue = "") String staffName, @RequestParam(defaultValue = "") String staffId, @RequestParam(defaultValue = "") String telePhone, @RequestParam(defaultValue = "") String cityName,
								   @RequestParam(defaultValue = "") String level, @RequestParam(defaultValue = "") String upperDepartmentNo, @RequestParam(defaultValue = "") String status, @RequestParam(defaultValue = "") String departmentType) {
		Map<String, Object> params = new HashMap<>();
		params.put("page", page);
		params.put("limit", limit);
		params.put("departmentName", departmentName);
		params.put("staffName", staffName);
		params.put("staffId", staffId);
		params.put("telePhone", telePhone);
		params.put("cityName", cityName);
		params.put("level", level);
		params.put("upperDepartmentNo", upperDepartmentNo);
		params.put("status", status);
		params.put("departmentType", departmentType);
		ResultPage resultPage = departmentService.searchDepartment(new QueryParam(params));
		return new Result().ok().put("page", resultPage).put("DepartmentTypeEnum", CommonEnums.toEnumMap(CommonEnums.DepartmentType.values())).put("StatusEnum",CommonEnums.toEnumMap(CommonEnums.CompanyStatus.values())).put("LevelEnum",CommonEnums.toEnumMap(CommonEnums.DepartmentLevel.values()));
	}

	/*
	 * description:
	 * @author jing.luo01@ucarinc.com
	 * @date   2019/8/5 20:01
	 * @params request 描述
	 * @param: id 查找的部门主键
	 * @param: page 前端传过来的PAGE数据
	 * @param: limit LIMIT语句限制数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("selectDepartment.do_")
	public Result selectDepartment(HttpServletRequest request, @RequestParam(defaultValue = "") String id, @RequestParam(defaultValue = "") String page, @RequestParam(defaultValue = "") String limit) {
		Map<String, Object> params = new HashMap<>();
		params.put("page", page);
		params.put("limit", limit);
		params.put("id", id);
		DepartmentDto departmentDto = departmentService.getDepartmentDtoById(id);
		ResultPage resultPage = companyManageService.getCompanyList(new QueryParam(params));
		//return new Result().ok().put("page", resultPage).put("department", departmentDto).put("DepartmentTypeEnum",CommonEnums.toEnumMap(CommonEnums.DepartmentType.values())).put("LevelEnum",CommonEnums.toEnumMap(CommonEnums.DepartmentLevel.values())).put("StatusEnum",CommonEnums.toEnumMap(CommonEnums.CompanyStatus.values()));
		return new Result().ok().put("page", resultPage).put("department", departmentDto).put("CompanyTypeEnum",CommonEnums.toEnumMap(CommonEnums.CompanyType.values())).put("CompanyStatusEnum",CommonEnums.toEnumMap(CommonEnums.CompanyStatus.values())).put("CompanyMarkEnum",CommonEnums.toEnumMap(CommonEnums.CompanyMark.values())).put("CompanyNatureEnum",CommonEnums.toEnumMap(CommonEnums.CompanyNature.values()));
	}
}
