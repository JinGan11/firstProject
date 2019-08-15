package com.ucar.combination.controller;

import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.RoleApply;
import com.ucar.combination.model.dto.*;
import com.ucar.combination.service.RoleApplyManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

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
            String accountName = (String) session.getAttribute("accountName");
            params.put("accountName", accountName);
        }
        List<RoleApplyDto> roleDtoList = roleApplyManageService.getRoleList(new QueryParam(params));
        ResultPage resultPage = roleApplyManageService.queryList(new QueryParam(params));
        return new Result().ok().put("page", resultPage)
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
        String stringId = request.getParameter("selection");
        int id = Integer.parseInt(stringId);
        roleApplyManageService.deleteRoleApply(id);
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
        String stringId = request.getParameter("selection");
        int id = Integer.parseInt(stringId);
        roleApplyManageService.commitRoleApply(id);
    }

    /**
    * @Description:  角色申请 新建 保存
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  16:02 2019/8/8
    */
    @ResponseBody
    @RequestMapping(value = "/createRoleApply.do_",method = RequestMethod.POST)
    public void createRoleApply(@RequestBody CreateRoleApplyDto createRoleApplyDto,HttpSession session){
        // 新建角色申请
        Long accountId = (Long) session.getAttribute("accountId");
        createRoleApplyDto.setModifyStaffName(accountId);//修改人ID
        createRoleApplyDto.setApplyStatus(1);
        //设置申请编号
        String roleApplyNum=roleApplyManageService.getApplyNum();
        createRoleApplyDto.setRoleApplyNum(roleApplyNum);
        roleApplyManageService.createRoleApply(createRoleApplyDto);
        //插入账号
        for(int i=0;i<createRoleApplyDto.getAccountIdList().length;i++){
            ApplyRoleAccountDto applyRoleAccountDto=new ApplyRoleAccountDto();
            applyRoleAccountDto.setApplyId(createRoleApplyDto.getId());//申请编号id
            applyRoleAccountDto.setRoleId(createRoleApplyDto.getRoleId());//角色id
            applyRoleAccountDto.setAccountId(createRoleApplyDto.getAccountIdList()[i]);//账号id
            applyRoleAccountDto.setApplyOperation(createRoleApplyDto.getApplyOperationList()[i]);//申请操作
            applyRoleAccountDto.setCreateEmp((Long) session.getAttribute("accountId"));//新建人
            applyRoleAccountDto.setModifyEmp((Long) session.getAttribute("accountId"));//修改人
            roleApplyManageService.createApplyRoleAccount(applyRoleAccountDto);
        }
    }

    /**
    * @Description:  角色申请  新建   保存并提交
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  8:38 2019/8/15
    */
    @ResponseBody
    @RequestMapping(value = "/createSaveCommitRoleApply.do_",method = RequestMethod.POST)
    public void createSaveCommitRoleApply(@RequestBody CreateRoleApplyDto createRoleApplyDto,HttpSession session){
        // 新建角色申请
        Long accountId = (Long) session.getAttribute("accountId");
        createRoleApplyDto.setModifyStaffName(accountId);//修改人ID
        createRoleApplyDto.setApplyStatus(2);
        //设置申请编号
        String roleApplyNum=roleApplyManageService.getApplyNum();
        createRoleApplyDto.setRoleApplyNum(roleApplyNum);
        roleApplyManageService.createRoleApply(createRoleApplyDto);
        //插入账号
        for(int i=0;i<createRoleApplyDto.getAccountIdList().length;i++){
            ApplyRoleAccountDto applyRoleAccountDto=new ApplyRoleAccountDto();
            applyRoleAccountDto.setApplyId(createRoleApplyDto.getId());//申请编号id
            applyRoleAccountDto.setRoleId(createRoleApplyDto.getRoleId());//角色id
            applyRoleAccountDto.setAccountId(createRoleApplyDto.getAccountIdList()[i]);//账号id
            applyRoleAccountDto.setApplyOperation(createRoleApplyDto.getApplyOperationList()[i]);//申请操作
            applyRoleAccountDto.setCreateEmp((Long) session.getAttribute("accountId"));//新建人
            applyRoleAccountDto.setModifyEmp((Long) session.getAttribute("accountId"));//修改人
            roleApplyManageService.createApplyRoleAccount(applyRoleAccountDto);
        }
    }


    /**
    * @Description:  角色申请 修改页面 账户列表
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  11:27 2019/8/9
    */
    @ResponseBody
    @RequestMapping("/showAccountListByApplyId.do_")
    public Result showAccountListByApplyId(HttpServletRequest request) {
        String strid = request.getParameter("applyId");
        Long applyId = Long.parseLong(strid);
        List<AccountListByApplyIdDto> list = roleApplyManageService.showAccountListByApplyId(applyId);
        return Result.ok().put("list", list);
    }


    /**
    * @Description: 查询登录信息
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  20:37 2019/8/9
    */
    @ResponseBody
    @RequestMapping("/queryLoginInRoleApply.do_")
    public Result queryLoginInRoleApply(HttpServletRequest request){
        String applyAccountName=request.getParameter("applyAccountName");
        LoginInfoInRoleApplyDto loginInfoInRoleApplyDto = roleApplyManageService.queryLoginInRoleApply(applyAccountName);
        return Result.ok().put("page",loginInfoInRoleApplyDto);
    }

    /**
    * @Description:  修改
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  14:42 2019/8/13
    */
    @ResponseBody
    @RequestMapping(value = "/modifyRoleApply.do_",method = RequestMethod.POST)
    public void modifyRoleApply(@RequestBody CreateRoleApplyDto createRoleApplyDto,HttpSession session){
        createRoleApplyDto.setApplyStatus(1);
        roleApplyManageService.modifyRoleApply(createRoleApplyDto);
        //修改之前先删除所有的账号
        roleApplyManageService.deleteAccountListInModifyApply(createRoleApplyDto.getId());
        //插入账号
        for(int i=0;i<createRoleApplyDto.getAccountIdList().length;i++){
            ApplyRoleAccountDto applyRoleAccountDto=new ApplyRoleAccountDto();
            applyRoleAccountDto.setApplyId(createRoleApplyDto.getId());//申请id
            applyRoleAccountDto.setRoleId(createRoleApplyDto.getRoleId());//角色id
            applyRoleAccountDto.setAccountId(createRoleApplyDto.getAccountIdList()[i]);//账号id
            applyRoleAccountDto.setApplyOperation(createRoleApplyDto.getApplyOperationList()[i]);//申请操作
            roleApplyManageService.createApplyRoleAccount(applyRoleAccountDto);
        }
    }

    /**
    * @Description:  角色申请  修改  保存并提交
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  8:51 2019/8/15
    */
    @ResponseBody
    @RequestMapping(value = "/modifySaveCommitRoleApply.do_",method = RequestMethod.POST)
    public void modifySaveCommitRoleApply(@RequestBody CreateRoleApplyDto createRoleApplyDto,HttpSession session){
        createRoleApplyDto.setApplyStatus(2);
        roleApplyManageService.modifyRoleApply(createRoleApplyDto);
        //修改之前先删除所有的账号
        roleApplyManageService.deleteAccountListInModifyApply(createRoleApplyDto.getId());
        //插入账号
        for(int i=0;i<createRoleApplyDto.getAccountIdList().length;i++){
            ApplyRoleAccountDto applyRoleAccountDto=new ApplyRoleAccountDto();
            applyRoleAccountDto.setApplyId(createRoleApplyDto.getId());//申请id
            applyRoleAccountDto.setRoleId(createRoleApplyDto.getRoleId());//角色id
            applyRoleAccountDto.setAccountId(createRoleApplyDto.getAccountIdList()[i]);//账号id
            applyRoleAccountDto.setApplyOperation(createRoleApplyDto.getApplyOperationList()[i]);//申请操作
            roleApplyManageService.createApplyRoleAccount(applyRoleAccountDto);
        }
    }
}
