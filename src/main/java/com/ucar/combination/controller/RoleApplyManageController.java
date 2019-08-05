package com.ucar.combination.controller;

import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.service.RoleApplyManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * description:角色申请管理
 *
 * @author min.zhang08@ucarinc.com
 * @date: Create in 9:43 now
 **/
@Controller
@RequestMapping("/roleApply")
public class RoleApplyManageController {
    @Autowired(required = false)
    private RoleApplyManageService roleApplyManageService;

    @ResponseBody
    @RequestMapping("/querylist.do_")
    public Result list(HttpServletRequest request) {
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        //请求界面中的数据
        String roleApplyNum = request.getParameter("roleApplyNum");
        String roleId = request.getParameter("roleId");
        String roleName = request.getParameter("roleName");
        String applyAccountName = request.getParameter("applyAccountName");
        String applyStaffNum = request.getParameter("applyStaffNum");
        String applyStaffName = request.getParameter("applyStaffName");
        String applyDepartmentName = request.getParameter("applyDepartmentName");
        String applyStatus = request.getParameter("applyStatus");
        String applyTime = request.getParameter("applyTime");
        String modifyTime = request.getParameter("modifyTime");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        params.put("limit", limit);
        //对应前端界面中的  未其赋值  查询时 后面的占位符
        params.put("roleApplyNum", roleApplyNum);
        params.put("roleId", roleId);
        params.put("roleName", roleName);
        params.put("applyAccountName", applyAccountName);
        params.put("applyStaffNum", applyStaffNum);
        params.put("applyStaffName", applyStaffName);
        params.put("applyDepartmentName", applyDepartmentName);
        params.put("applyStatus", applyStatus);
        params.put("applyTime", applyTime);
        params.put("modifyTime", modifyTime);
        ResultPage resultPage = roleApplyManageService.queryList(new QueryParam(params));
        return new Result().ok().put("page", resultPage).put("BusinessLineEnum", CommonEnums.toEnumMap(CommonEnums.BusinessLineEnum.values()))
                .put("applyStatusEnum", CommonEnums.toEnumMap(CommonEnums.applyStatusEnum.values()));
    }
}
