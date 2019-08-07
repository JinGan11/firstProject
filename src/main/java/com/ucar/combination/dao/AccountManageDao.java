package com.ucar.combination.dao;

import com.ucar.combination.model.Account;
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
    void updateStatus(int id);

    List<RoleAccount> getRoleAccountList(Map<String, Object> map);
}
