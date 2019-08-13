package com.ucar.combination.controller;

import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.*;
import com.ucar.combination.model.dto.RolePowerDto;
import com.ucar.combination.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ucar.combination.common.CommonMethod.objectToMap;

/**
 * @version 1.0
 * @description: 权限控制类
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-08-05 16:40
 */
@RestController
@RequestMapping("/power")
public class PowerController {

    @Autowired
    PowerService powerService;

    /**
     * description: 获取所有权限信息
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/5 22:40
     * @params 无
     * @return 返回结果集
     */
    @RequestMapping("/getPowerList")
    public Result getPowerList() {
        PowerTree rootNode = powerService.buildPowerTree();
        return new Result().put("powerTree", rootNode);
    }

    /**
     * description: 获取账号已有的权限
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/7 14:59
     * @params
     * @return
     */
    @RequestMapping("/getAccountPower.do_")
    public Result getAccountPower(@RequestBody Account account) {
        return powerService.getAccountPower(account);
    }

    /**
     * description: 为账号添加角色
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/5 22:40
     * @params role 角色
     * @return
     */
    @RequestMapping("/modifyAccountRole")
    public Result modifyAccountRole(@RequestBody RoleList roleList, HttpSession session) {
        Long accountId = (Long) session.getAttribute("accountId");
        Result result = powerService.modifyAccountRole(roleList,accountId);
        return result;
    }

    /**
     * description: 修改账户特殊权限
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/5 22:51
     * @params
     * @return
     */
    @RequestMapping("/modifySpecialPower")
    public Result modifySpecialPower(@RequestBody PowerList powerList, HttpSession session) {
        Long accountId = (Long) session.getAttribute("accountId");
        Result result = powerService.modifySpecialPower(powerList,accountId);
        return result;
    }
    /**
     * description:用于接收数据返回前端列表
     *
     * @param request 前端请求
     * @return com.ucar.combination.common.Result 返回数据
     * @author gan.jin@ucarinc.com
     * @date 2019/8/7 15:18
     */

    @RequestMapping(value = "/queryRolePowerlist.do_" ,method = RequestMethod.POST)
    public Result queryRolePowerList(@RequestBody RolePowerDto rolePowerDto, HttpServletRequest request) {
////
////
 //         Long=role_id = Long.parseLong(request.getParameter("roleId"));
 //     String role_name = request.getParameter("roleName");
////        String business_line = request.getParameter("businessLine");
////  int role_status = Integer.parseInt(request.getParameter("roleStatus"));
////        String approver_account_name = request.getParameter("approverAccountName");
////        String approver_staff_num = request.getParameter("approverStaffNum");
////        String approver_staff_name = request.getParameter("approverStaffName");
////        String approver_department_name = request.getParameter("approverDepartmentName");
////        String power_name = request.getParameter("powerName");
////        Map<String, Object> params = new HashMap<String, Object>();
////        params.put("role_id", role_id);
////        params.put("role_name", role_name);
////        params.put("business_line", business_line);
////        params.put("role_status", role_status);
////        params.put("approver_account_name", approver_account_name);
////        params.put("approver_staff_num", approver_staff_num);
////        params.put("approver_staff_name", approver_staff_name);
////        params.put("approver_department_name", approver_department_name);
////        params.put("power_name",power_name);
//        ResultPage resultPage = powerService.queryRolePowerList(new QueryParam(params));
            ResultPage resultPage = powerService.queryRolePowerList(rolePowerDto);
            List<RolePowerDto> rolePowerDtoList = powerService.getRolePowerDtoList(rolePowerDto);


        return new Result().ok().put("page", resultPage).put("RoleStatusEnum", CommonEnums.toEnumMap(CommonEnums.RoleStatusEnum.values()))
                .put("rolePowerDtoList",rolePowerDtoList);


    }

}
