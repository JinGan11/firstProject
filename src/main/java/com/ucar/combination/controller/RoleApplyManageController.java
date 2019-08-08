package com.ucar.combination.controller;

import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.dto.RoleApplyDto;
import com.ucar.combination.service.RoleApplyManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
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
    public Result list(HttpServletRequest request, HttpSession session) {
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
        String applyTimeStart = request.getParameter("applyTimeSatrt");
        String applyTimeEnd = request.getParameter("applyTimeEnd");
        String modifyTimeStart = request.getParameter("modifyTimeStart");
        String modifyTimeEnd = request.getParameter("modifyTimeEnd");
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
        params.put("applyTimeStart", applyTimeStart);
        params.put("applyTimeEnd", applyTimeEnd);
        params.put("modifyTimeStart", modifyTimeStart);
        params.put("modifyTimeEnd", modifyTimeEnd);

        if (request.getParameter("type").equals("角色审核")) {
            Long accountId = (Long) session.getAttribute("accountId");
            params.put("accountId", accountId);
        }
        List<RoleApplyDto> roleDtoList = roleApplyManageService.getRoleList(new QueryParam(params));
        ResultPage resultPage = roleApplyManageService.queryList(new QueryParam(params));
        return new Result().ok().put("page", resultPage).put("BusinessLineEnum", CommonEnums.toEnumMap(CommonEnums.BusinessLineEnum.values()))
                .put("applyStatusEnum", CommonEnums.toEnumMap(CommonEnums.applyStatusEnum.values()))
                .put("roleDtoList", roleDtoList);
    }

    /**
    * @Description:  删除
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  15:35 2019/8/7
    */
    @ResponseBody
    @RequestMapping("/deleteRoleApply.do_")
    public void deleteRoleApply(HttpServletRequest request){
        String roleApplyNum = request.getParameter("selection");
        roleApplyManageService.deleteRoleApply(roleApplyNum);
    }
    
    /**
    * @Description:  提交角色申请
    * @Author: min.zhang08@ucarinc.com  
    * @Params  
    * @Return  
    * @Date  17:40 2019/8/7
    */
    @ResponseBody
    @RequestMapping("/commitRoleApply.do_")
    public void commitRoleApply(HttpServletRequest request){
        String roleApplyNum = request.getParameter("selection");
        roleApplyManageService.commitRoleApply(roleApplyNum);
    }
}
