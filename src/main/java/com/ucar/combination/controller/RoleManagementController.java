package com.ucar.combination.controller;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.service.RoleManagementService;
import com.ucar.combination.service.impl.RoleManagementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 角色管理
 *
 * @author gan.jin@ucarinc.com
 * @version 1.0
 * @Date 2019/7/31 16:47
 */
@Controller
@RequestMapping("/roleManage")
public class RoleManagementController {

    @Autowired(required = false)
    private RoleManagementServiceImpl roleManagementService;

    @ResponseBody
    @RequestMapping("/querylist.do_")
    public Result list(HttpServletRequest request){
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String roleName=request.getParameter("roleName");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        params.put("limit", limit);
        params.put("role_name",roleName);
        ResultPage resultPage = roleManagementService.queryList(new QueryParam(params));
        return Result.ok().put("page",resultPage);
    }

    @ResponseBody
    @RequestMapping("/updateStatus.do_")
    public void update(HttpServletRequest request){
        String strid = request.getParameter("selection");
        if (strid != "") {
            int id = Integer.parseInt(strid);
            roleManagementService.updateStatus(id);
        } else{
        }
    }
}
