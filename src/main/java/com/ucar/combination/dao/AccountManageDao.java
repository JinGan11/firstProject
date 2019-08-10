package com.ucar.combination.dao;

import com.ucar.combination.model.Account;
import com.ucar.combination.model.AccountStaff;
import com.ucar.combination.model.DepartmentPower;
import com.ucar.combination.model.RoleAccount;

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
    /*
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

    /*
     * description: 通过账户id查询账户数据
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：  2019/8/8
     * @PArams： 账户id
     * @Return： com.ucar.combination.model.Account
     */
    public Account selectAccountById(String id);

    List<RoleAccount> getRoleAccountList(Map<String, Object> map);

    /**
     * description：插入账户历史记录
     * @author qingyu.lan@ucarinc.com
     * @date   2019/8/8 01：05
     * @param map
     * @return
     */
    int insertAccountHistory (Map<String, Object> map);
    /*
     * description: 新建账户
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：  2019-8-6
     * @PArams： map
     * @Return：
     */
    int insertAccount(AccountStaff accountStaff);

    /*
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

    /*
     * description: 修改账户信息
     * @author peng.zhang11@ucarinc.com
     * @date:  2019/8/9 11:25
     * @params: accountStaff 要修改的信息
     * @return:
    */
    int modifyAccount(AccountStaff accountStaff);

    /*
     * description: 修改账户信息
     * @author peng.zhang11@ucarinc.com
     * @date:  2019/8/9 11:25
     * @params: accountStaff 要修改的信息
     * @return:
     */
     int insertDepartmentPower(DepartmentPower departmentPower);
}
