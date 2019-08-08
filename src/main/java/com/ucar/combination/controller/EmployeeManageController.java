package com.ucar.combination.controller;

import com.alibaba.fastjson.JSON;
import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;

import com.ucar.combination.model.Account;
import com.ucar.combination.model.Staff;
import com.ucar.combination.service.AccountManagerService;
import com.ucar.combination.service.EmployeeManageService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private AccountManagerService accountManagerService;

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
        String accountName = request.getParameter("accountName");
        String is_dimission = request.getParameter("isDimission");
        String department = request.getParameter("departmentId");
        String upper_department_no = request.getParameter("upper_department_no");
        String relAccount = request.getParameter("relAccount");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        params.put("limit", limit);
        params.put("satff_num", staff_num);
        params.put("staff_name", staff_name);
        params.put("accountName", accountName);
        params.put("is_dimission", is_dimission);
        params.put("department", department);
        params.put("upper_department_no", upper_department_no);
        params.put("relAccount",relAccount);
        ResultPage resultPage = employeeManageService.queryList(new QueryParam(params));
        List<Object> staffDtoList = employeeManageService.getStaffList(new QueryParam(params));
        return new Result().ok().put("page", resultPage).put("SexEnum", CommonEnums.toEnumMap(CommonEnums.Sex.values()))
                .put("isDismissionEnum", CommonEnums.toEnumMap(CommonEnums.isDimission.values()))
                .put("staffDtoList", objectToMap(staffDtoList));
    }
    /**
     * description:在员工表中插入新员工
     * @author shiya.li@ucarinc.com
     * @date   2019/8/5 10:37
     * @params staff 员工对象
     * @return 插入成功，返回到员工列表页
     */
    @ResponseBody
    @RequestMapping(value = "/insertStaff",method = RequestMethod.POST)
    public  String insertStaff(@RequestBody Staff staff){
        employeeManageService.insertStaff(staff);
        System.out.println("insertStaff:"+ JSON.toJSONString(staff));
        return "redirect:querylist.do_";
    }
    /**
     * description:在员工表更改员工状态为无效
     * @author shiya.li@ucarinc.com
     * @date   2019/8/7 9:47
     * @params 员工id
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateStatus.do_")
    public Result update(HttpServletRequest request){
        String strid = request.getParameter("id");
        String straccountId=request.getParameter("accountId");

        int id = Integer.parseInt(strid);
        int accountId=Integer.parseInt(straccountId);
        System.out.print("id:"+id);
        System.out.print("accountId:"+accountId);
        employeeManageService.updateStatus(id);
        accountManagerService.updateStatus(accountId);
        return Result.ok();
    }

    /**
     * description: 员工离职
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/8 9:43
     * @params 员工ID
     * @return
     */
    @ResponseBody
    @RequestMapping("/quitEmployee.do_")
    public Result quitEmployee(HttpServletRequest request, @RequestParam String id){
        Integer status1=employeeManageService.updateDimission(Long.valueOf(id));
        Integer status2=accountManagerService.updateState(id);
        Staff staff=employeeManageService.selectById(id);
        Account account=accountManagerService.selectById(staff.getAccountId());
        /*account_id,
                staff_num,
                staff_name,
                permissions,
                history_operation_type,
                create_emp,
                create_time*/
        Map<String,Object> param=new HashMap<>();
        param.put("accountId",staff.getAccountId());
        param.put("staffNum",staff.getStaffNum());
        param.put("staffName",staff.getStaffName());
        param.put("permissions",account.getPremissions());
        param.put("historyOperationType","8");
        param.put("createEmp",request.getSession().getAttribute("accountId"));
        Integer status3=accountManagerService.insertAccountHistory(param);
        if (status1==0&status2==0&status3==1){
            return Result.ok().put("status","success");
        }else {
            return Result.ok().put("status","error");
        }
    }
}
