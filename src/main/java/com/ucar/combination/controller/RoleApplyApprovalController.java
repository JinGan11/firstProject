package com.ucar.combination.controller;

import com.ucar.combination.common.Result;
import com.ucar.combination.service.RoleApplyApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * description:角色审批
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-27 17:00:21
 **/
@Controller
@RequestMapping("/roleApplyApproval")
public class RoleApplyApprovalController {
    @Autowired
    private RoleApplyApprovalService roleApplyApprovalService;

    /**
     * description:角色审批通过
     * @author qingyu.lan@ucarinc.com
     * @date   2019/8/14 10:35
     * @params request session
     * @return Result
     */
    @ResponseBody
    @RequestMapping("/approvalPass.do_")
    public Result approvalPass(HttpServletRequest request,HttpSession session){
        String id = request.getParameter("id");
        String roleId = request.getParameter("roleId");
        String ids = request.getParameter("accountIds");
        String deIds = request.getParameter("removeAccountIds");
        String[] accountIds = ids.split(",");
        String[] removeAccountIds =deIds.split(",");
        Long accountId = (Long) session.getAttribute("accountId");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("roleId", roleId);
        params.put("accountIds", accountIds);
        params.put("accountId", accountId);
        params.put("removeAccountIds",removeAccountIds);
        roleApplyApprovalService.approvalPass(params);
        return Result.ok().put("msg", "success");
    }

    /**
     * description:角色审批拒绝
     * @author qingyu.lan@ucarinc.com
     * @date   2019/8/14 10:37
     * @params request session
     * @return Result
     */
    @ResponseBody
    @RequestMapping("/approvalReject.do_")
    public Result approvalReject(HttpServletRequest request,HttpSession session){
        String id = request.getParameter("id");
        String rejectReason = request.getParameter("rejectReason");
        Long accountId = (Long) session.getAttribute("accountId");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("rejectReason", rejectReason);
        params.put("accountId", accountId);
        roleApplyApprovalService.approvalReject(params);
        return Result.ok().put("msg", "success");
    }
}
