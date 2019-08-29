package com.ucar.combination.service;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.*;
import com.ucar.combination.model.Account;
import com.ucar.combination.model.AccountStaff;
import com.ucar.combination.model.Staff;
import com.ucar.combination.model.dto.AccountPowerDto;
import org.apache.ibatis.annotations.Param;

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
     * @PArams：queryParam 查询条件
     * @Return：ResultPage 查询结果
     */
    ResultPage queryList(QueryParam queryParam);

    /**
     * description: 账户管理excel
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date： 2019/8/15 12:25
     * @PArams：queryParam 查询条件
     * @Return：List 查询结果
     */
    List<Account> getAccountList(QueryParam queryParam);

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

    /**
     * description: 查询角色账号明细
     * @uthor： zhengyong.liao@ucarinc.com
     * @Date：  2019/8/7
     * @PArams：
     * @Return：
     */
    ResultPage getRoleAccountList(QueryParam queryParam);

    /**
     * description: 历史记录
     * @uthor： zhengyong.liao@ucarinc.com
     * @Date：  2019/8/7
     * @PArams：
     * @Return：
     */
    List<RoleAccount> queryRoleAccountList(QueryParam queryParam);

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

    //冻结和解冻的历史记录
    void lockAndUnlock(int id, int status, String historyOperationType, HttpServletRequest request);

    /**
     * description: 查询账户名是否存在
     * @author junqiang.zhang@ucarinc.com
     * @date:  2019/8/13 10:41
     * @params: accountName 账户名
     * @return: int 查询到的账户条数
     */
    int selectAccountByAccountName(String accountName);
    /**
     * description: 员工更改邮箱地址，关联的有效账号的邮箱同步更新
     * @author shiya.li@ucarinc.com
     * @date:  2019/8/14 22:40
     * @params: accountName 账户名
     * @return: List<String> 查询到的账户
     */
    void updateAccountSecretEmailById(String secretEmail,String id);
    /**
     * description: 得到所有的AccountPowerDto的信息
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/15 9:48
     * @params queryParam 描述

     * @return AccountPowerDto的LIST集合
     */
    List<AccountPowerDto> getAllAccountPowerList(QueryParam queryParam);

    //返回历史记录
    List<AccountHistory> getHistoryList(String accountId);
    void updateModifyTimeAndModifyName(@Param("a") Account account);
    Long selectStaffIdById(Long accountId);

    Result updateAccountStatue(Long accountId, Long operateAccountId);

    /**
     * description: 根据账户id查询账户状态
     * @author junqiang.zhang@ucarinc.com
     * @date: 2019/8/20
     * @params: accountId 账户id
     * @return: int 账户状态
     */
    int getAccountStateById(Long accountId);

    /**
     * description: 根据员工id查询员工信息
     * @author junqiang.zhang@ucarinc.com
     * @date: 2019/8/20
     * @params: staffId 员工id
     * @return: Staff 离职，是否删除，账户id信息
     */
    int getStaffInfBystaffId(Long staffId);

    Account selectAccountByNum(String accountName);

    void insertResetPass(ResetPass resetPass);
}
