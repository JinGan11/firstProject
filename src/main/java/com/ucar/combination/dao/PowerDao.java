package com.ucar.combination.dao;

import com.ucar.combination.model.AccountPower;
import com.ucar.combination.model.Power;
import com.ucar.combination.model.PowerTree;

import java.util.List;

public interface PowerDao {

    /**
     * description: 获取所有权限
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/6 11:56
     * @params
     * @return
     */
    List<PowerTree> getPowerList();

    List<AccountPower> getAccountPowerListById(Long accountId);

    void insertAccountPower(AccountPower accountPower);

    void removeAccountPowerById(AccountPower accountPower);

}
