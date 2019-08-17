package com.ucar.combination.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.Department;
import com.ucar.combination.model.dto.*;
import com.ucar.combination.service.CompanyManageService;
import com.ucar.combination.service.DepartmentService;
import com.ucar.combination.service.RegionManageService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
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
    @Autowired
    RegionManageService regionManageService;

    /**
     * description: 构建树结构的部门
     *
     * @return 树结构根节点
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/6 19:18
     * @params
     */
    @ResponseBody
    @RequestMapping("/buildTree.do_")
    public Result list() {
        DepartmentTreeDto rootNode = departmentService.buildTree();
        return new Result().put("departmentDto", rootNode);
    }

    /**
     * description: 添加一个新的部门
     *
     * @return
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/6 19:18
     * @params
     */
    @ResponseBody
    @RequestMapping("/addDepartment.do_")
    public Map<String, Object> addDepartment(@RequestBody Department department, HttpSession session) {
        Long accountId = (Long) session.getAttribute("accountId");
        department.setCreateEmp(accountId);
        department.setModifyEmp(accountId);
        Map<String, Object> map = departmentService.checkInput(department);
        if ((Boolean) map.get("result"))
            departmentService.insertDepartment(department);
        return map;
    }

    /**
     * description: 根据部门编号查询支持的业务线
     *
     * @return
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/8 14:50
     * @params
     */
    @ResponseBody
    @RequestMapping("/getSupportBusiness.do_")
    public String getSupport(String departmentNo) {
        return departmentService.selectSupportBusiness(departmentNo);
    }

    /**
     * description: 删除部门（逻辑删除），会导致其下面的部门无法显示
     *
     * @return
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/6 19:19
     * @params
     */
    @ResponseBody
    @RequestMapping("/deleteDepartment.do_")
    public String deleteDepartment(@RequestBody Department department) {
        departmentService.deleteDepartment(department.getId());
        return "success";
    }

    /**
     * description: 构建部门树结构，专门用于修改上级部门
     *
     * @return
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/8 9:39
     * @params
     */
    @ResponseBody
    @RequestMapping("/buildUpperTree.do_")
    public Result listUpper(Long id) {
        DepartmentUpperDto rootNode = departmentService.buildUpperTree(id);
        return new Result().put("departmentDto", rootNode);
    }

    /**
     * description: 修改上级部门
     *
     * @return 修改是否成功
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/8 10:41
     * @params 需要修改的部门的id，上级部门的编号
     */
    @ResponseBody
    @RequestMapping("/updateUpperDepartment.do_")
    public Map<String, Object> changeUpper(@RequestBody Department department) {
        Map<String, Object> map = departmentService.checkWorkplaceForUpper(department.getId(), department.getUpperDepartmentNo());
        if ((Boolean) map.get("result")){
            if(!departmentService.updateUpperDepartment(department.getId(), department.getUpperDepartmentNo())){
                map.put("result",false);
                map.put("msg","部门的有效性发生变更，修改失败，请返回查看！");
            }
        }
        return map;
    }

    /**
     * description: 查找单个部门详细信息
     *
     * @return 部门详情、及冗余信息
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/8 19:53
     * @params 部门id
     */
    @ResponseBody
    @RequestMapping("/selectDepartmentById.do_")
    public Result selectDepartmentbyId(Long id) {
        DepartmentEditDto departmentEdit = departmentService.selectDepartmentForEdit(id);
        return new Result().put("departmentEdit", departmentEdit);
    }

    /**
     * description: 修改部门详细信息
     *
     * @return
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/12 15:00
     * @params
     */
    @ResponseBody
    @RequestMapping("/updateDepartment.do_")
    public Map<String, Object> updateDepartment(@RequestBody Department department, HttpSession session) {
        Long accountId = (Long) session.getAttribute("accountId");
        department.setModifyEmp(accountId);

        Map<String, Object> map = departmentService.checkInput(department);
        if ((Boolean) map.get("result"))
            departmentService.updateDepartment(department);
        return map;
    }

    /**
     * description: 建立部门树用于其他模块调用
     *
     * @return
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/12 17:11
     * @params
     */
    @ResponseBody
    @RequestMapping("/buildTree2.do_")
    public Result buildTree2() {
        DepartmentTree2Dto rootNode = departmentService.buildTree2();
        return new Result().put("departmentDto", rootNode);
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
    public Result searchDepartment(HttpServletRequest request, @RequestParam(defaultValue = "") String page, @RequestParam(defaultValue = "") String limit, @RequestParam(defaultValue = "") String departmentName, @RequestParam(defaultValue = "") String staffName, @RequestParam(defaultValue = "") String staffId, @RequestParam(defaultValue = "") String telephone, @RequestParam(defaultValue = "") String cityName,
                                   @RequestParam(defaultValue = "") String level, @RequestParam(defaultValue = "") String upperDepartmentNo, @RequestParam(defaultValue = "") String status, @RequestParam(defaultValue = "") String departmentType) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("limit", limit);
        params.put("departmentName", departmentName);
        params.put("staffName", staffName);
        params.put("staffId", staffId);
        params.put("telephone", telephone);
        params.put("cityName", cityName);
        params.put("level", level);
        params.put("upperDepartmentNo", upperDepartmentNo);
        params.put("status", status);
        params.put("departmentType", departmentType);
        ResultPage resultPage = departmentService.searchDepartment(new QueryParam(params));
        List<SearchDepartmentDto> listSearch=departmentService.searchDepartmentAll(new QueryParam(params));
        return new Result().ok().put("page", resultPage).put("DepartmentTypeEnum", CommonEnums.toEnumMap(CommonEnums.DepartmentType.values())).put("StatusEnum", CommonEnums.toEnumMap(CommonEnums.DepartmentStatus.values())).put("LevelEnum", CommonEnums.toEnumMap(CommonEnums.DepartmentLevel.values())).put("listSearch",listSearch);
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
    @RequestMapping("/selectDepartment.do_")
    public Result selectDepartment(HttpServletRequest request, @RequestParam(defaultValue = "") String id) {
        DepartmentDto departmentDto = departmentService.getDepartmentDtoById(id);
        return new Result().ok().put("department", departmentDto).put("CompanyTypeEnum", CommonEnums.toEnumMap(CommonEnums.CompanyType.values())).put("CompanyStatusEnum", CommonEnums.toEnumMap(CommonEnums.CompanyStatus.values())).put("CompanyMarkEnum", CommonEnums.toEnumMap(CommonEnums.CompanyMark.values())).put("CompanyNatureEnum", CommonEnums.toEnumMap(CommonEnums.CompanyNature.values())).put("DepartmentTypeEnum", CommonEnums.toEnumMap(CommonEnums.DepartmentType.values())).put("LevelEnum", CommonEnums.toEnumMap(CommonEnums.DepartmentLevel.values())).put("DepartmentStatusEnum", CommonEnums.toEnumMap(CommonEnums.DepartmentStatus.values()));
    }

    /**
     * description: 得到城市的信息
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/14 15:30
     * @params
     * @return
     */
    @ResponseBody
    @RequestMapping("/getCityList.do_")
     public Result getCityList(){
        List<Object> cityList=regionManageService.getCityList();
        return Result.ok().put("cityList",cityList);
    }
}
