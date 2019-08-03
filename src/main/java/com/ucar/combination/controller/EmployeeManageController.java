package com.ucar.combination.controller;

import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;

import com.ucar.combination.service.EmployeeManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ucar.combination.common.CommonMethod.objectToMap;

/**
 * description:员工管理列表
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-27 17:00:21
 **/
@Controller
@RequestMapping("/employee")
public class EmployeeManageController {
    @Autowired
    private EmployeeManageService employeeManageService;

    /**
     * 查询员工列表
     * @param request
     * @return
     * @throws IllegalAccessException
     */
    @ResponseBody
    @RequestMapping("/querylist.do_")
    public Result list(HttpServletRequest request) throws IllegalAccessException {
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String staff_num = request.getParameter("staffNum");
        String staff_name = request.getParameter("staffName");
        String accountId = request.getParameter("accountId");
        String is_dimission = request.getParameter("isDimission");
        String department = request.getParameter("departmentId");
        String upper_department_no = request.getParameter("upper_department_no");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        params.put("limit", limit);
        params.put("satff_num", staff_num);
        params.put("staff_name", staff_name);
        params.put("accountId", accountId);
        params.put("is_dimission", is_dimission);
        params.put("department", department);
        params.put("upper_department_no", upper_department_no);
        ResultPage resultPage = employeeManageService.queryList(new QueryParam(params));
        List<Object> staffDtoList = employeeManageService.getStaffList(new QueryParam(params));
        return new Result().ok().put("page", resultPage).put("SexEnum", CommonEnums.toEnumMap(CommonEnums.Sex.values()))
                .put("isDismissionEnum", CommonEnums.toEnumMap(CommonEnums.isDimission.values()))
                .put("staffDtoList", objectToMap(staffDtoList));
    }

}
