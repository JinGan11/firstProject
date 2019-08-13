package com.ucar.combination.service;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.*;
import com.ucar.combination.model.Account;
import com.ucar.combination.model.AccountStaff;
import com.ucar.combination.model.Staff;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * description:账户管理
 *
 * @Author junqiang.zhang@ucarinc.com
 * @Version 1.0
 * @Date: 2019/8/1
 **/
public interface AccountManagerService {
    /**
     * description: 账户管理列表
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：
     * @PArams：
     * @Return：
     */
    ResultPage queryList(QueryParam queryParam);

    /**
     * description: 通过账户id查询账户数据
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：  2019/8/8
     * @PArams： 账户id
     * @Return： com.ucar.combination.model.Account
     */
    public Account selectAccountById(String id);

    /**
     * description: 新建账户,并返回账户id
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：  2019/8/7
     * @PArams：账户信息
     * @Return：账户id
     */
    public void insertAccount(AccountStaff accountStaff);

    ResultPage getRoleAccountList(QueryParam queryParam);
    /**
     * description:由账户id将账户状态设置为无效
     * @author shiya.li@ucarinc.com
     * @date   2019/8/6 17：32
     * @params id 账户id
     * @return 无
     */
    void updateStatus(int id,int status);

    /**
     * description: 设置员工账号
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/8 9:57
     * @params id 员工ID

     * @return 状态
     */
    int updateState(String id);
    /**
     * description: 查找Account
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/8 10:31
     * @params 账户ID
     * @return  ACCOUNT实体
     */
    Account selectById(Long id);

    /**
     * description: 插入历史记录
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/8 10:45
     * @params map 描述

     * @return
     */
    int insertAccountHistory (AccountStaff accountStaff);

    /**
     * description: 修改账户信息
     * @author junqiang.zhang@ucarinc.com
     * @date:  2019/8/9 11:25
     * @params: accountStaff 要修改的信息
     * @return:
     */
    int modifyAccount(AccountStaff accountStaff);

    /**
     * description: 修改员工的账户信息
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date： 2019/8/9 13:04
     * @params accountStaff 要修改的信息
     * @return
     */
    int updateStaffAccount(AccountStaff accountStaff);
    /**
     * description: 账户权限明细
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/9 15:08
     * @params
     * @return
     */
    ResultPage getAccountPowerList(QueryParam queryParam);

    /**
     * description: 手动选择数据权限新增
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：  2019/8/10 10:25
     * @PArams：accountId 账户id tree部门权限
     * @Return：
     */
    void insertDepartmentPower(Long accountId,String tree);

    /**
     * description: 手动选择数据权限删除权限
     * @author junqiang.zhang@ucarinc.com
     * @date:  2019/8/10 10:55
     * @params: accountId 账户id
     * @return:
     */
    int deleteDepartmentPower(Long accountId);

    /**
     * description: 查询账户的部门权限
     * @author junqiang.zhang@ucarinc.com
     * @date:  2019/8/10 12:30
     * @params: accountId 账户id
     * @return: List 账户拥有的部门权限的部门id
     */
    List<String> selectDeparentPowerByAccountId(Long accountId);

    /**
     * description: 删除账户（账户状态改为无效）
     * @author junqiang.zhang@ucarinc.com
     * @date:  2019/8/10 14:30
     * @params: accountId 账户id和修改人id
     * @return:
     */
    int deleteAccountById(AccountStaff accountStaff);

    void lockAndUnlock(int id, int status, String historyOperationType, HttpServletRequest request);


}
