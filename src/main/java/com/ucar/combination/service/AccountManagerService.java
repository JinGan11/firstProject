package com.ucar.combination.service;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.Account;
import com.ucar.combination.model.Account;
import com.ucar.combination.model.AccountStaff;
import com.ucar.combination.model.Staff;

import java.util.Map;

/**
 * description:账户管理
 *
 * @Author junqiang.zhang@ucarinc.com
 * @Version 1.0
 * @Date: 2019/8/1
 **/
public interface AccountManagerService {
    /*
     * description: 账户管理列表
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：
     * @PArams：
     * @Return：
     */
    ResultPage queryList(QueryParam queryParam);

    /*
     * description: 通过账户id查询账户数据
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：  2019/8/8
     * @PArams： 账户id
     * @Return： com.ucar.combination.model.Account
     */
    public Account selectAccountById(String id);

    /*
     * description: 新建账户,并返回账户id
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：  2019/8/7
     * @PArams：账户信息
     * @Return：账户id
     */
    public Long insertAccount(AccountStaff accountStaff);

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
    int insertAccountHistory (Map<String, Object> map);

    /*
     * description: 修改账户信息
     * @author peng.zhang11@ucarinc.com
     * @date:  2019/8/9 11:25
     * @params: accountStaff 要修改的信息
     * @return:
     */
    int modifyAccount(AccountStaff accountStaff);

    /*
     * description: 修改员工的账户信息
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date： 2019/8/9 13:04
     * @params accountStaff 要修改的信息
     * @return
     */
    int updateStaffAccount(AccountStaff accountStaff);
}
