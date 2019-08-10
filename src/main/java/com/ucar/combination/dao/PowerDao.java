package com.ucar.combination.dao;

import com.ucar.combination.model.AccountPower;
import com.ucar.combination.model.Power;
import com.ucar.combination.model.PowerTree;
import com.ucar.combination.model.dto.AccountPowerDto;
import com.ucar.combination.model.dto.RolePowerDto;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
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
    /**
     * description: 账户权限明细查询
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/9 15:11
     * @params
     * @return
     */
    List<AccountPowerDto> getAccountPowerList(Map<String, Object> map);
    /**
     * description: 角色权限明细查询
     * @author shiya.li@ucarinc.com
     * @date   2019/8/10 17:08
     * @params
     * @return
     */
    List<RolePowerDto> queryRolePowerList(Map<String, Object> map);

}
