package com.ucar.combination.service.impl;

import com.ucar.combination.common.Result;
import com.ucar.combination.dao.PowerDao;
import com.ucar.combination.dao.RoleManagementDao;
import com.ucar.combination.model.*;
import com.ucar.combination.service.PowerService;
import com.ucar.combination.utils.PowerTreeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @description: 权限服务层
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-08-05 16:42
 */
@Service
public class PowerServiceImpl implements PowerService {

    @Autowired
    PowerDao powerDao;

    @Autowired
    RoleManagementDao roleManagementDao;

    /**
     * description: 构造权限树
     *
     * @return 权限树
     * @author peng.zhang11@ucarinc.com
     * @date 2019/8/6 12:03
     * @params 无
     */
    @Override
    public PowerTree buildPowerTree() {
        List<PowerTree> list = powerDao.getPowerList();
        return new PowerTreeBuilder().buildTree(list);
    }

    /**
     * description: 修改账户拥有的角色
     *
     * @return
     * @author peng.zhang11@ucarinc.com
     * @date 2019/8/6 12:03
     * @params roleList 角色列表
     */
    @Override
    public Result modifyAccountRole(RoleList roleList, Long accountId) {
        //  1.先查询改账户已拥有的角色，如果roleList不在已拥有的角色中，则表示被移除需要调用删除语句删除角色
        //  2.再对比没有的角色，一条条插入账户中，
        List<Role> list = roleManagementDao.getAccountRoleListById(accountId);
        AccountRole accountRole = new AccountRole();
        // 如果该账户没有角色，则全部插入
        if (list == null || list.size() == 0) {
            for (int i = 0; i < roleList.getRoleList().size(); i++) {
                //插入该角色
                accountRole.setAccountId(accountId);
                accountRole.setRoleId(roleList.getRoleList().get(i).getRoleId());
                accountRole.setModifyEmp(accountId);
                roleManagementDao.insertAccountRole(accountRole);
            }
        } else {
            // 删除账户被移除的角色
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < roleList.getRoleList().size(); j++) {
                    if (list.get(i).getRoleId() == roleList.getRoleList().get(j).getRoleId()) {
                        break;
                    }
                    if (list.get(i).getRoleId() != roleList.getRoleList().get(j).getRoleId()
                            && j == roleList.getRoleList().size() -1) {
                        //删除该角色
                        accountRole.setAccountId(accountId);
                        accountRole.setRoleId(list.get(i).getRoleId());
                        roleManagementDao.removeAccountRoleById(accountRole);
                    }

                }
            }
            // 为账户插入新添加的角色
            for (int i = 0; i < roleList.getRoleList().size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    if (roleList.getRoleList().get(i).getRoleId() == list.get(j).getRoleId()) {
                        break;
                    }
                    if (roleList.getRoleList().get(i).getRoleId() != list.get(j).getRoleId()
                            && j == list.size() -1) {
                        //插入该角色
                        accountRole.setAccountId(accountId);
                        accountRole.setRoleId(roleList.getRoleList().get(i).getRoleId());
                        accountRole.setModifyEmp(accountId);
                        roleManagementDao.insertAccountRole(accountRole);
                    }
                }
            }
        }
        return null;
    }

    /**
     * description: 修改账户拥有的特殊权限
     *
     * @return
     * @author peng.zhang11@ucarinc.com
     * @date 2019/8/6 12:03
     * @params powerList 权限列表
     */
    @Override
    public Result modifySpecialPower(PowerList powerList, Long accountId) {
        //  1.查询该账户已拥有的特殊权限，如果powerList中有而数据库中没有，则表示删除该权限
        //  2.然后插入powerList中有的，而数据库中没有的
        List<AccountPower> list = powerDao.getAccountPowerListById(accountId);
        AccountPower accountPower = new AccountPower();
        // 如果该账户没有权限，则全部插入
        if (list == null || list.size() == 0) {
            for (int i = 0; i < powerList.getPowerList().size(); i++) {
                //插入该权限
                accountPower.setAccountId(accountId);
                accountPower.setPowerId(powerList.getPowerList().get(i).getPowerId());
                powerDao.insertAccountPower(accountPower);
            }
        } else {
            // 删除账户被移除的权限
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < powerList.getPowerList().size(); j++) {
                    if (list.get(i).getPowerId().equals(powerList.getPowerList().get(j).getPowerId())) {
                        break;
                    }
                    if (!list.get(i).getPowerId().equals(powerList.getPowerList().get(j).getPowerId())
                            && j == powerList.getPowerList().size() -1) {
                        //删除该角色
                        accountPower.setAccountId(accountId);
                        accountPower.setPowerId(list.get(i).getPowerId());
                        powerDao.removeAccountPowerById(accountPower);
                    }
                }
            }
            // 为账户插入新添加的权限
            for (int i = 0; i < powerList.getPowerList().size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    if (powerList.getPowerList().get(i).getPowerId().equals(list.get(j).getPowerId())) {
                        break;
                    }
                    if (!powerList.getPowerList().get(i).getPowerId().equals(list.get(j).getPowerId())
                            && j == list.size() -1) {
                        //插入该权限
                        accountPower.setAccountId(accountId);
                        accountPower.setPowerId(powerList.getPowerList().get(i).getPowerId());
                        powerDao.insertAccountPower(accountPower);
                    }
                }
            }
        }
        return null;
    }
}
