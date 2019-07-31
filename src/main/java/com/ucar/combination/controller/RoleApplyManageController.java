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

@Controller
@RequestMapping("/roleApply")
public class RoleApplyManageController {

    @Autowired(required = false)
    private RoleApplyManageService roleApplyManageService;

    @ResponseBody
    @RequestMapping("/querylist.do_")
    public Result list(HttpServletRequest request){
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        params.put("limit", limit);
        ResultPage resultPage = roleApplyManageService.queryList(new QueryParam(params));
//        return new Result().ok().put("page", resultPage);
        return Result.ok().put("page",resultPage);
    }
}
