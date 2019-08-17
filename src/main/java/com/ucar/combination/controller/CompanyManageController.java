package com.ucar.combination.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.Company;
import com.ucar.combination.model.Department;
import com.ucar.combination.model.dto.BusinessLicense;
import com.ucar.combination.service.CompanyManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

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
    /**
     * description: 依据查询条件获取公司信息
     * @author: jianan.shu@ucarinc.com
     * @param:
     * @date: 2019/8/8 9:01
     * @return：
     */
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
    /**
     * description: 新建公司
     * @author: jianan.shu@ucarinc.com
     * @param:
     * @date: 2019/8/8 11:23
     * @return：
     */
    @ResponseBody
    @RequestMapping(value = "/createCompany",method = RequestMethod.POST)
    public Result createCompany(@RequestParam("businessLicenses") MultipartFile[] businessLicenses,
                                @RequestParam("company") String data ,HttpSession session){
        Company company = JSON.parseObject(data, Company.class);
        if(businessLicenses.length > 20) {
             return new Result().ok().put("msg","outLimit");
        }
        //信用代码唯一性校验
        Map<String,Object>map=companyManageService.creditCodeValidate(company.getCreditCode());
        if((Boolean)map.get("result")){
            companyManageService.insertCompany(businessLicenses, data,session);
        }
        return new Result().ok().put("list",map);
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
        List<BusinessLicense> licenses = companyManageService.getIdsByCompanyId(companyId);
        Map<String,Object>list=companyManageService.getCompanyById(companyId);
        return Result.ok().put("list",list).put("licenses",licenses);
    }
    /**
     * description: 修改公司信息
     * @author: jianan.shu@ucarinc.com
     * @param:
     * @date: 2019/8/8 9:26
     * @return：
     */
    @ResponseBody
    @RequestMapping(value = "/modifyCompany",method = RequestMethod.POST)
    public Result updateCompanyById(@RequestParam("businessLicenses") MultipartFile[] businessLicenses,
                                    @RequestParam("company") String data ,HttpSession session){
        Company company = JSON.parseObject(data, Company.class);
        //信用代码唯一性校验,先判断是否更改，如更改则需判断唯一性，否则直接更新公司
        Map<String,Object>map=new HashMap<>();
        if(company.getOldCreditCode().equals(company.getCreditCode())) {
            companyManageService.updateCompanyById(businessLicenses, data, session);
        }else{
            map = companyManageService.creditCodeValidate(company.getCreditCode());
            if((Boolean) map.get("result")){
                companyManageService.updateCompanyById(businessLicenses, data, session);
            }else{
                return new Result().ok().put("msg", "fail");
            }
        }

        return new Result().ok().put("msg", "success");
    }
    /**
     * description: 查询未关联公司列表
     * @author: jianan.shu@ucarinc.com
     * @param:
     * @date: 2019/8/14 11:12
     * @return：
     */
    @ResponseBody
    @RequestMapping("/queryRelationList.do_")
    public Result searchCompanyQuery(HttpServletRequest request) {
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String departmentId = request.getParameter("departmentId");
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
        params.put("departmentId", departmentId);
        params.put("companyName", companyName);
        params.put("creditCode",creditCode);
        params.put("companyType", companyType);
        params.put("companyNature", companyNature);
        params.put("companyStatus", companyStatus);
        params.put("startTime", startTime);
        params.put("endTime", endTime);

        ResultPage resultPage = companyManageService.queryRelationList(new QueryParam(params));
        return new Result().ok().put("page", resultPage).put("CompanyTypeEnum", CommonEnums.toEnumMap(CommonEnums.CompanyType.values()))
                .put("CompanyNatureEnum", CommonEnums.toEnumMap(CommonEnums.CompanyNature.values()))
                .put("CompanyMarkEnum", CommonEnums.toEnumMap(CommonEnums.CompanyMark.values()))
                .put("CompanyStatusEnum", CommonEnums.toEnumMap(CommonEnums.CompanyStatus.values()));
    }
    /**
     * description: 查询部门关联公司列表
     * @author: jianan.shu@ucarinc.com
     * @param:
     * @date: 2019/8/14 11:11
     * @return：
     */
    @ResponseBody
    @RequestMapping("/querylistRel.do_")
    public Result relationCompanyQuery(HttpServletRequest request) {
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String departmentId = request.getParameter("departmentId");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        params.put("limit", limit);
        params.put("departmentId", departmentId);

        ResultPage resultPage = companyManageService.relationCompanyList(new QueryParam(params));
        return new Result().ok().put("page", resultPage).put("CompanyTypeEnum", CommonEnums.toEnumMap(CommonEnums.CompanyType.values()))
                .put("CompanyNatureEnum", CommonEnums.toEnumMap(CommonEnums.CompanyNature.values()))
                .put("CompanyMarkEnum", CommonEnums.toEnumMap(CommonEnums.CompanyMark.values()))
                .put("CompanyStatusEnum", CommonEnums.toEnumMap(CommonEnums.CompanyStatus.values()));
    }

    /**
     * description: 处理关联公司保存
     * @author: jianan.shu@ucarinc.com
     * @param:
     * @date: 2019/8/14 11:11
     * @return：
     */
    @ResponseBody
    @RequestMapping(value = "/saveRelation.do_")
    public String saveRelation(HttpServletRequest request, HttpSession session) {
        Long accountId = (Long) session.getAttribute("accountId");
        String departmentId = (String)request.getParameter("departmentId");
        String oldRelation=request.getParameter("oldRelationList");
        String [] oldRelationArry=oldRelation.split(",");
        String newRelation=request.getParameter("newRelationList");
        String [] newRelationArry=newRelation.split(",");
        List<String> oldRelationList=new ArrayList<>();
        Collections.addAll(oldRelationList,oldRelationArry);
        List<String> newRelationList=new ArrayList<>();
        Collections.addAll(newRelationList,newRelationArry);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("departmentId", departmentId);
        params.put("newRelationList", newRelationList);
        params.put("oldRelationList", oldRelationList);
        params.put("accountId", accountId);

        companyManageService.saveRelations(params);
        return "success";
    }
    /**
     * 输出文件流
     * @param response
     * @param id
     */
    @RequestMapping("/getLicense")
    public void getLicenseById(HttpServletResponse response, @RequestParam("id") long id)  {
        String fileUrl = companyManageService.getFileUrlById(id);
        OutputStream out = null;
        InputStream in = null;
        try {
            //获取IO流进行读入输出
            out = response.getOutputStream();
            File file = new File(fileUrl);
            in = new FileInputStream(file);
            byte[] b = new byte[1024];
            while (in.read(b) != -1) {
                out.write(b);
            }
            out.flush();
        } catch (IOException e) {
        } finally {
            try {
                if (out != null) out.close();
                if (in != null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
     /**
     * 删除文件
     * @param
     * @param id
     */
    @RequestMapping("/deleteLicense.do_")
    @ResponseBody
    public Result deleteLicense (@RequestParam("id") Long id) {
        companyManageService.deleteLicense(id);
        return Result.ok().put("msg", "success");
    }
        /**
     * description: 返回COMPANY的分页查询语句
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/16 22:33
     * @params page 起始页
     * @param: limit 多少条数据
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryCompanyList.do_")
    public Result queryCompanyList(@RequestParam(defaultValue = "") String id, @RequestParam(defaultValue = "") String page, @RequestParam(defaultValue = "") String limit){
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("limit", limit);
        params.put("id", id);
        ResultPage resultPage = companyManageService.getCompanyList(new QueryParam(params));
        return Result.ok().put("page", resultPage);
    }
}
