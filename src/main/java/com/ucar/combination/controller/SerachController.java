package com.ucar.combination.controller;

import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.service.AccountManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public Result getRoleAccountList(HttpServletRequest request){
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
                .put("permissionEnum",CommonEnums.toEnumMap(CommonEnums.Permission.values()))
                .put("accountStatusEnum",CommonEnums.toEnumMap(CommonEnums.AccountStatusEnum.values()))
                .put("accountStatusList",CommonEnums.toJsonList(CommonEnums.AccountStatusEnum.values()));
    }

}
