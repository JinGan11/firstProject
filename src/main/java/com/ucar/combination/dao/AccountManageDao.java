package com.ucar.combination.dao;

import com.ucar.combination.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * description:账户管理列表
 *
 * @Author junqiang.zhang@ucarinc.com
 * @Version 1.0
 * @Date: 2019/7/31 17:42
 **/
public interface AccountManageDao {
    /**
     * description: 账户列表
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：  2019-8-1
     * @PArams： map
     * @Return：
     */
    List<Account> queryList(Map<String, Object> map);
    /**
     * description:由账户id将账户状态设置为无效
     * @author shiya.li@ucarinc.com
     * @date   2019/8/6 17：28
     * @params id 账户id
     * @return 无
     */
    void updateStatus(int id, int status);

    /**
     * description: 通过账户id查询账户数据
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：  2019/8/8
     * @PArams： 账户id
     * @Return： com.ucar.combination.model.Account
     */
    public Account selectAccountById(String id);

    /**
     * description: 角色账号明细查询
     * @uthor： zhengyong.liao@ucarinc.com
     * @Date：  2019/8/8
     * @PArams： map
     * @Return： com.ucar.combination.model.roleAccount
     */
    List<RoleAccount> getRoleAccountList(Map<String, Object> map);

    /**
     * description：插入账户历史记录
     * @author qingyu.lan@ucarinc.com
     * @date   2019/8/8 01：05
     * @param map
     * @return
     */
    int insertAccountHistory (AccountStaff accountStaff);
    /**
     * description: 新建账户
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：  2019-8-6
     * @PArams： map
     * @Return：
     */
    int insertAccount(AccountStaff accountStaff);

    /**
     * description: 通过用户名查询用户的id
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：  2019-8-7
     * @PArams： 用户名
     * @Return： 用户id
     */
    Long selectIdByNum(String accountNum);

    /**
     * description: 账号状态改变
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/8 9:59
     * @params Id 账号ID
     * @return 账号状态
     */
    int updateState(String id);

    /**
     * description: 查找账户
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/8 10:36
     * @params id 账户ID
     * @return
     */
    Account selectById(Long id);

    /**
     * description: 修改账户信息
     * @author junqiang.zhang@ucarinc.com
     * @date:  2019/8/9 11:25
     * @params: accountStaff 要修改的信息
     * @return:
    */
    int modifyAccount(AccountStaff accountStaff);

    /**
     * description: 手动选择数据权限新增
     * @author junqiang.zhang@ucarinc.com
     * @date:  2019/8/10 10:25
     * @params: departmentPower 账户id和新增的部门权限
     * @return:
     */
     int insertDepartmentPower(DepartmentPower departmentPower);
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
     * @params: accountId 账户id
     * @return:
     */
    int deleteAccountById(AccountStaff accountStaff);

    /**
     * description: 查询数据权限类型
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/10 17:07
     * @params 用户的account_id
     * @return
     */
    Integer selectPermissionsById(Long id);
    /*
     用于插入历史记录之前查询出账号相应信息
     */
    AccountHistory selectAccountInfoById(Integer id);

    void updateModifyTimeAndModifyName(@Param("a") Account account);
    /**
     * description: 查询账户名是否存在
     * @author junqiang.zhang@ucarinc.com
     * @date:  2019/8/13 10:41
     * @params: accountName 账户名
     * @return: List<String> 查询到的账户
    */
    List<String> selectAccountByAccountName(String accountName);
    /**
     * description: 员工更改邮箱地址，关联的有效账号的邮箱同步更新
     * @author shiya.li@ucarinc.com
     * @date:  2019/8/14 22:40
     * @params: 密保邮箱，账户id
     * @return:
     */
     void updateAccountSecretEmailById(String secretEmail,String id);

    /**
     * description: 根据账户id查询员工id
     * @author junqiang.zhang@ucarinc.com
     * @date: 2019/8/18
     * @params: accountId 账户id
     * @return: Long 员工id
     */
    Long selectStaffIdById(Long accountId);

    /**
     * description: 根据账户id查询账户状态
     * @author junqiang.zhang@ucarinc.com
     * @date: 2019/8/20
     * @params: accountId 账户id
     * @return: int 账户状态
     */
    int getAccountStateById(Long accountId);


    //根据员工ID获取员工状态
    //int selectAccountStatusById(long accountId);
    /**
     * description: 根据账户ID查询员工名字
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/22 15:36
     * @params id 账户ID

     * @return
     */
    String getStaffNameByAccountId(Long id);

    Account selectAccountByNum(String accountName);

    /**
     * description:  根据id修改账户关联员工的信息
     * @author junqiang.zhang@ucarinc.com
     * @date:   2019/8/28 15:36
     * @params: id 账户id
     * @return:
    */
    int updataStaffIdById(Long id);

    void insertResetPass(ResetPass resetPass);
}
