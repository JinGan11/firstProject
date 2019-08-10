package com.ucar.combination.controller;

import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.service.AccountManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/roleAccount")
public class SerachController {

	@Resource
	private AccountManagerService accountManagerService;

	@ResponseBody
	@RequestMapping("/getRoleAccountList.do")
	public Result getRoleAccountList(HttpServletRequest request) {
		String roleName = request.getParameter("roleName");
		String businessLine = request.getParameter("businessLine");
		String accountName = request.getParameter("accountName");
		String staffNum = request.getParameter("staffNum");
		String staffName = request.getParameter("staffName");
		String roleStatus = request.getParameter("roleStatus");
		String accountState = request.getParameter("accountState");
		String departmentName = request.getParameter("departmentName");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleName", roleName);
		params.put("businessLine", businessLine);
		params.put("accountName", accountName);
		params.put("staffNum", staffNum);
		params.put("staffName", staffName);
		params.put("roleStatus", roleStatus);
		params.put("departmentId", departmentName);
		params.put("accountState", accountState);
		params.put("page", 1);
		params.put("limit", 10);
		ResultPage resultPage = accountManagerService.getRoleAccountList(new QueryParam(params));
		return new Result().ok().put("page", resultPage)
				.put("permissionList", CommonEnums.toJsonList(CommonEnums.Permission.values()))
				.put("permissionEnum", CommonEnums.toEnumMap(CommonEnums.Permission.values()))
				.put("accountStatusEnum", CommonEnums.toEnumMap(CommonEnums.AccountStatusEnum.values()))
				.put("accountStatusList", CommonEnums.toJsonList(CommonEnums.AccountStatusEnum.values()));
	}

	/**
	 * description: 查询账号权限明细
	 *
	 * @return
	 * @author jing.luo01@ucarinc.com
	 * @date 2019/8/9 15:07
	 * @params request HTTPSERVLET方法
	 * @param: accountName 账户名
	 * @param: page 第几页
	 * @param: limit 多少行
	 */
	@ResponseBody
	@RequestMapping("/getAccountPowerList.do_")
	public Result getAccountPowerList(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		//String a=request.getParameter("accountName");@RequestParam String accountName, @RequestParam String page, @RequestParam String limit
		String page=request.getParameter("page");
		String limit=request.getParameter("limit");
		String accountName=request.getParameter("accountName");
		String staffNum=request.getParameter("staffNum");
		String staffName=request.getParameter("staffName");
		String departmentName=request.getParameter("departmentName");
		String powerName=request.getParameter("powerName");
		String accountState=request.getParameter("accountState");
		param.put("page", page);
		param.put("limit", limit);
		param.put("accountName", accountName);
		param.put("staffNum",staffNum);
		param.put("staffName",staffName);
		param.put("departmentName",departmentName);

		param.put("powerName",powerName);
		param.put("accountState",accountState);
		ResultPage resultPage = accountManagerService.getAccountPowerList(new QueryParam(param));
		if (resultPage.getList()!=null){
			return Result.ok().put("page",resultPage).put("status","success").put("AccountStatusEnums",CommonEnums.toEnumMap(CommonEnums.AccountStatusEnum.values()));
		}else {
			return Result.ok().put("status","error");
		}

	}
}
