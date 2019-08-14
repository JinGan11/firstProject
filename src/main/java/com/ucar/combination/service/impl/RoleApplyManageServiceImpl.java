package com.ucar.combination.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.RoleApplyManageDao;
import com.ucar.combination.model.dto.*;
import com.ucar.combination.service.RoleApplyManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * description:角色申请管理
 *
 * @author min.zhang08@ucarinc.com
 * @date: Create in 9:39 now
 **/
@Service
public class RoleApplyManageServiceImpl implements RoleApplyManageService {
    @Autowired(required = false)
    private RoleApplyManageDao roleApplyManageDao;


    @Override
    public ResultPage queryList(QueryParam queryParam) {
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<RoleApplyDto> list = roleApplyManageDao.queryList(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }
    /**
     * description：导出角色申请列表
     * @author shiya.li@ucarinc.com
     * @param queryParam
     * @return
     */
    public List<RoleApplyDto> getRoleList(QueryParam queryParam){
        return roleApplyManageDao.queryList(queryParam);
    }



    /**
    * @Description:  删除
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  15:26 2019/8/7
    */
    @Override
    public void deleteRoleApply(int id) {
        roleApplyManageDao.deleteRoleApply(id);
    }

    /**
    * @Description:  提交审核
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  17:41 2019/8/7
    */
    @Override
    public void commitRoleApply(int id) {
        roleApplyManageDao.commitRoleApply(id);
    }

    /**
    * @Description:  角色申请 新建
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  15:41 2019/8/8
    */
    @Override
    public void createRoleApply(CreateRoleApplyDto createRoleApplyDto) {

        roleApplyManageDao.createRoleApply(createRoleApplyDto);
    }

    /**
    * @Description:  角色申请 修改页面  账户列表
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  11:17 2019/8/9
    */
    @Override
    public List showAccountListByApplyId(Long applyId) {
        List<AccountListByApplyIdDto> accountListByApplyIdDtos=roleApplyManageDao.showAccountListByApplyId(applyId);
        return accountListByApplyIdDtos;
    }

    /**
    * @Description:  查询当前登录账号的 具体信息
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  18:19 2019/8/10
    */
    @Override
    public LoginInfoInRoleApplyDto queryLoginInRoleApply(String applyAccountName) {
        LoginInfoInRoleApplyDto loginInfoInRoleApplyDto = roleApplyManageDao.queryLoginInRoleApply(applyAccountName);
        return loginInfoInRoleApplyDto;
    }

    /**
     * @Description:  角色申请 新建 为角色添加账户
     * @Author: min.zhang08@ucarinc.com
     * @Params
     * @Return
     * @Date  18:18 2019/8/10
     */
    @Override
    public void createApplyRoleAccount(ApplyRoleAccountDto applyRoleAccountDto) {
        roleApplyManageDao.createApplyRoleAccount(applyRoleAccountDto);
    }

    /**
    * @Description:  修改
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  14:42 2019/8/13
    */
    @Override
    public void modifyRoleApply(CreateRoleApplyDto createRoleApplyDto) {
        roleApplyManageDao.modifyRoleApply(createRoleApplyDto);
    }


    /**
    * @Description:  角色申请  修改   修改之前先删除之前选过的账户
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  9:23 2019/8/14
    */
    @Override
    public void deleteAccountListInModifyApply(Long applyId) {
        roleApplyManageDao.deleteAccountListInModifyApply(applyId);
    }

    /**
    * @Description:  角色申请 当天总记录数
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  19:16 2019/8/14
    */
    @Override
    public int getTodayApplyCount() {
        Integer todayApplyCount=roleApplyManageDao.getTodayApplyCount();
        return todayApplyCount;
    }

    /**
    * @Description:  角色申请 申请编号
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  19:11 2019/8/14
    */
    @Override
    public String getApplyNum() {
        Integer todayApplyCount=roleApplyManageDao.getTodayApplyCount();
        //角色申请顺序编号5位
        int numCount=5;
        StringBuffer applyNum=new StringBuffer("SQ");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        applyNum.append(sdf.format(new Date()));
        //如果今日申请数不为0，则加一
        if(todayApplyCount!=0){
            todayApplyCount=todayApplyCount+1;
            for(int i=numCount;i>todayApplyCount.toString().length();i--){
                applyNum.append("0");
            }
            applyNum.append(todayApplyCount.toString());
        }else{
            //如果今日无申请记录，则直接拼接编号00001
            applyNum.append("00001");
        }
        return applyNum.toString();
    }


}
