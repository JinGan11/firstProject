package com.ucar.combination.service;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.Account;
import com.ucar.combination.model.PowerList;
import com.ucar.combination.model.PowerTree;
import com.ucar.combination.model.RoleList;
import com.ucar.combination.model.dto.RolePowerDto;

import java.util.List;
import java.util.Map;

public interface PowerService {

    PowerTree buildPowerTree();

    Result modifyAccountRole(PowerList powerList,Long accountId);

    Result modifySpecialPower(PowerList powerList,Long accountId);

    Result modifyPermission(PowerList powerList,Long accountId);

    Result getAccountPower(Account account);

    Result getAccountAllPermission(Long accountId);

    ResultPage queryRolePowerList(QueryParam queryParam);
    ResultPage queryRolePowerList(RolePowerDto rolePowerDto);
}
