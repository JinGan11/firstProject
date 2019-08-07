package com.ucar.combination.controller;

import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.AssignPermission;
import com.ucar.combination.model.PowerList;
import com.ucar.combination.model.RolePower;
import com.ucar.combination.model.dto.RoleDto;
import com.ucar.combination.service.impl.RoleManagementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    /**
     * description:用于接收数据返回前端列表
     * @author gan.jin@ucarinc.com
     * @date 2019/8/7 15:18
     * @param request 前端请求
     * @return com.ucar.combination.common.Result 返回数据
     */

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
        return Result.ok().put("page",resultPage).put("RoleStatusEnum", CommonEnums.toEnumMap(CommonEnums.RoleStatusEnum.values()));
    }

    /**
     * description:用于逻辑删除数据
     * @author gan.jin@ucarinc.com
     * @date 2019/8/7 15:20
     * @param request 前端请求
     * @return void
     */

    @ResponseBody
    @RequestMapping("/updateStatus.do_")
    public void update(HttpServletRequest request){
        String strid = request.getParameter("selection");
            int id = Integer.parseInt(strid);
            roleManagementService.updateStatus(id);
    }

    /**
     * description: 用于插入数据
     * @author gan.jin@ucarinc.com
     * @date 2019/8/7 15:21
     * @param role 前端请求传入对象role
     * @return void
     */

    @ResponseBody
    @RequestMapping(value = "/insertRole.do_",method = RequestMethod.POST)
    public void insertRole(@RequestBody RoleDto role){
        roleManagementService.insertRole(role);
       // System.out.println("insertRole:"+ JSON.toJSONString(role));
    }

    /**
     * description:将选中的数据返回到修改页面
     * @author gan.jin@ucarinc.com
     * @date 2019/8/7 15:22
     * @param request 前端请求
     * @return com.ucar.combination.common.Result 处理结果
     */

    @ResponseBody
    @RequestMapping("/getOneInf.do_")
    public Result getOneInf(HttpServletRequest request){
        String strid = request.getParameter("roleID");
        int id = Integer.parseInt(strid);
        RoleDto roleDto = roleManagementService.getOneInf(id);
        System.out.println("ASDFA");
        return Result.ok().put("page",roleDto);
    }

    /**
     * description:修改后的数据插入
     * @author gan.jin@ucarinc.com
     * @date 2019/8/7 15:23
     * @param role  传入对象
     * @return void
     */

    @ResponseBody
    @RequestMapping(value = "/updateByModify.do_",method = RequestMethod.POST)
    public void updateByModify(@RequestBody RoleDto role){
        roleManagementService.updateByModify(role);
        // System.out.println("insertRole:"+ JSON.toJSONString(role));
    }

    /**
     * description: 为角色分配权限
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/5 22:51
     * @params
     * @return
     */
    @ResponseBody
    @RequestMapping("/assignPermission")
    public Result assignPermission(@RequestBody AssignPermission assignPermission, HttpSession session) {
        Long accountId = (Long) session.getAttribute("accountId");
        Result result = roleManagementService.assignPermission(assignPermission,accountId);
        return result;
    }

    /**
     * description: 获取角色已拥有的权限
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/7 14:49
     * @params
     * @return
     */
    @ResponseBody
    @RequestMapping("/getRolePower.do_")
    public Result getRolePower(@RequestBody RolePower rolePower, HttpSession session) {
        return roleManagementService.getRolePower(rolePower);
    }
}
