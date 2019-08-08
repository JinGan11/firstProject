package com.ucar.combination.service;

import com.ucar.combination.common.Result;
import com.ucar.combination.model.Account;
import com.ucar.combination.model.PowerList;
import com.ucar.combination.model.PowerTree;
import com.ucar.combination.model.RoleList;

public interface PowerService {

    PowerTree buildPowerTree();

    Result modifyAccountRole(RoleList roleList,Long accountId);

    Result modifySpecialPower(PowerList powerList,Long accountId);

    Result getAccountPower(Account account);

    Result getAccountAllPermission(Long accountId);
}
