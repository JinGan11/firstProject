package com.ucar.combination.controller;

import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.Company;
import com.ucar.combination.model.Department;
import com.ucar.combination.service.CompanyManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * description:
 *
 * @author jianan.shu@ucarinc.com
 * @version 1.0
 * @date: 2019/8/3 10:23
*/
@Controller
@RequestMapping("/company")
public class CompanyManageController {
    @Autowired
    private CompanyManageService companyManageService;

    @ResponseBody
    @RequestMapping("/querylist.do_")
    public Result list(HttpServletRequest request) {
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String companyName = request.getParameter("companyName");
        String creditCode = request.getParameter("creditCode");
        String companyType = request.getParameter("companyType");
        String companyNature = request.getParameter("companyNature");
        String companyStatus = request.getParameter("companyStatus");
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        params.put("limit", limit);
        params.put("companyName", companyName);
        params.put("creditCode",creditCode);
        params.put("companyType", companyType);
        params.put("companyNature", companyNature);
        params.put("companyStatus", companyStatus);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        ResultPage resultPage = companyManageService.queryList(new QueryParam(params));
        return new Result().ok().put("page", resultPage).put("CompanyTypeEnum", CommonEnums.toEnumMap(CommonEnums.CompanyType.values()))
                .put("CompanyNatureEnum", CommonEnums.toEnumMap(CommonEnums.CompanyNature.values()))
                .put("CompanyMarkEnum", CommonEnums.toEnumMap(CommonEnums.CompanyMark.values()))
                .put("CompanyStatusEnum", CommonEnums.toEnumMap(CommonEnums.CompanyStatus.values()));
    }
    @ResponseBody
    @RequestMapping(value = "/createCompany",method = RequestMethod.POST)
    public String createCompany(@RequestBody Company company){
        companyManageService.insertCompany(company);
        return "success";
    }
    /**
     * description: 获取单一公司信息
     * @author: jianan.shu@ucarinc.com
     * @param:
     * @date: 2019/8/7 14:20
     * @return：
     */
    @ResponseBody
    @RequestMapping("/getCompanyById.do_")
    public Result getOneCompanyById(HttpServletRequest request){
        String id = request.getParameter("companyId");
        int companyId = Integer.parseInt(id);
        Company company = companyManageService.getCompanyById(companyId);
        return Result.ok().put("list",company);
    }
}
