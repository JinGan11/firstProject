package com.ucar.combination.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.PowerDao;
import com.ucar.combination.dao.RoleManagementDao;
import com.ucar.combination.model.*;
import com.ucar.combination.model.dto.RolePowerDto;
import com.ucar.combination.service.PowerService;
import com.ucar.combination.utils.PowerTreeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public Result modifyAccountRole(PowerList powerList, Long accountId) {
        //  1.先查询改账户已拥有的角色，如果roleList不在已拥有的角色中，则表示被移除需要调用删除语句删除角色
        //  2.再对比没有的角色，一条条插入账户中，
        List<Role> list = roleManagementDao.getAccountRoleListById(powerList.getId());
        AccountRole accountRole = new AccountRole();
        // 移除所有角色
        if (powerList.getRoleList().size() == 0) {
            roleManagementDao.removeAllAccountRoleById(powerList.getId());
        }
        // 如果该账户没有角色，则全部插入
        if (list == null || list.size() == 0) {
            for (int i = 0; i < powerList.getRoleList().size(); i++) {
                //插入该角色
                accountRole.setAccountId(powerList.getId());
                accountRole.setRoleId(powerList.getRoleList().get(i).getRoleId());
                accountRole.setModifyEmp(accountId);
                roleManagementDao.insertAccountRole(accountRole);
            }
        } else {
            // 删除账户被移除的角色
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < powerList.getRoleList().size(); j++) {
                    if (list.get(i).getRoleId() == powerList.getRoleList().get(j).getRoleId()) {
                        break;
                    }
                    if (list.get(i).getRoleId() != powerList.getRoleList().get(j).getRoleId()
                            && j == powerList.getRoleList().size() -1) {
                        //删除该角色
                        accountRole.setAccountId(powerList.getId());
                        accountRole.setRoleId(list.get(i).getRoleId());
                        roleManagementDao.removeAccountRoleById(accountRole);
                    }
                }
            }
            // 为账户插入新添加的角色
            for (int i = 0; i < powerList.getRoleList().size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    if (powerList.getRoleList().get(i).getRoleId() == list.get(j).getRoleId()) {
                        break;
                    }
                    if (powerList.getRoleList().get(i).getRoleId() != list.get(j).getRoleId()
                            && j == list.size() -1) {
                        //插入该角色
                        accountRole.setAccountId(powerList.getId());
                        accountRole.setRoleId(powerList.getRoleList().get(i).getRoleId());
                        accountRole.setModifyEmp(accountId);
                        roleManagementDao.insertAccountRole(accountRole);
                    }
                }
            }
        }
        return Result.ok();
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
        List<AccountPower> list = powerDao.getAccountPowerListById(powerList.getId());
        AccountPower accountPower = new AccountPower();
        // 如果该账户没有权限，则全部插入
        if (list == null || list.size() == 0) {
            for (int i = 0; i < powerList.getPowerList().size(); i++) {
                //插入该权限
                accountPower.setAccountId(powerList.getId());
                accountPower.setPowerId(powerList.getPowerList().get(i).getPowerId());
                try{
                    powerDao.insertAccountPower(accountPower);
                } catch (Exception e) {
                    throw e;
                }
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
                        accountPower.setAccountId(powerList.getId());
                        accountPower.setPowerId(list.get(i).getPowerId());
                        try {
                            powerDao.removeAccountPowerById(accountPower);
                        } catch (Exception e) {
                            throw e;
                        }
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
                        accountPower.setAccountId(powerList.getId());
                        accountPower.setPowerId(powerList.getPowerList().get(i).getPowerId());
                        try {
                            powerDao.insertAccountPower(accountPower);
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                }
            }
        }
        return Result.ok();
    }

    @Override
    public Result modifyPermission(PowerList powerList, Long accountId) {
        Result result = new Result();
        modifyAccountRole(powerList,accountId);
        modifySpecialPower(powerList,accountId);
        return result.put("code",200);
    }

    /**
     * description: 获取账户拥有的特殊权限
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/7 14:21
     * @params
     * @return
     */
    @Override
    public Result getAccountPower(Account account) {
        //待完善accountID;要分配的账户的ID,不是修改人ID
        List<AccountPower> list = powerDao.getAccountPowerListById(account.getId());
        List accountPower = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            accountPower.add(list.get(i).getPowerId());
        }
        return Result.ok().put("accountPower", accountPower);
    }
    /**
     * description: 获取账户拥有的角色权限和特殊权限的集合
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/8 13:27
     * @params
     * @return
     */
    @Override
    public Result getAccountAllPermission(Long accountId) {
        //存储所有权限的权限ID
        List powerList = new ArrayList<>();
        //获取所有角色
        List<Role> list = roleManagementDao.getAccountRoleListById(accountId);
        Long roleInfoId;
        //获取角色的权限
        for (int k = 0; k < list.size(); k++) {
            roleInfoId = list.get(k).getRoleId();
            List<RolePower> rolePowerList = roleManagementDao.getRolePowerListById(roleInfoId);
            if (powerList == null || powerList.size() == 0) {
                for (int h = 0; h < rolePowerList.size(); h++) {
                    powerList.add(rolePowerList.get(h).getPowerId());
                }
            } else {
                for (int i = 0; i < rolePowerList.size(); i++) {
                    for (int j = 0; j < powerList.size(); j++) {
                        if (rolePowerList.get(i).getPowerId().equals(powerList.get(j))) {
                            break;
                        }
                        if (!rolePowerList.get(i).getPowerId().equals(powerList.get(j))
                                && j == powerList.size() -1) {
                            //插入该权限
                            powerList.add(rolePowerList.get(i).getPowerId());
                        }
                    }
                }
            }
        }
        //获取特殊权限
        List<AccountPower> accountPowerList =  powerDao.getAccountPowerListById(accountId);
        if (powerList == null || powerList.size() == 0) {
            for (int h = 0; h < accountPowerList.size(); h++) {
                powerList.add(accountPowerList.get(h).getPowerId());
            }
        } else {
            for (int i = 0; i < accountPowerList.size(); i++) {
                for (int j = 0; j < powerList.size(); j++) {
                    if (accountPowerList.get(i).getPowerId().equals(powerList.get(j))) {
                        break;
                    }
                    if (!accountPowerList.get(i).getPowerId().equals(powerList.get(j))
                            && j == powerList.size() -1) {
                        //插入该权限
                        powerList.add(accountPowerList.get(i).getPowerId());
                    }
                }
            }
        }
        return Result.ok().put("powerList",powerList);
    }

    /**
     * description: 获取角色的所有权限
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/8 13:51
     * @params
     * @return
     */
    List<Power> getAllRolePowerList() {
        List<Power> powerList = new ArrayList<>();
        return null;
    }

    /**
     * description: 整合权限，将相同的移除
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/8 14:02
     * @params
     * @return
     */
    List<Power> integrationPower(List powerList, List<?> roleOrAccountPowerList) {
//        if (powerList == null || powerList.size() == 0) {
//            for (int h = 0; h < roleOrAccountPowerList.size(); h++) {
//                powerList.add(roleOrAccountPowerList.get(h).getPowerId());
//            }
//        } else {
//            for (int i = 0; i < roleOrAccountPowerList.size(); i++) {
//                for (int j = 0; j < powerList.size(); j++) {
//                    if (roleOrAccountPowerList.get(i).getPowerId().equals(powerList.get(j))) {
//                        break;
//                    }
//                    if (!roleOrAccountPowerList.get(i).getPowerId().equals(powerList.get(j))
//                            && j == list.size() -1) {
//                        //插入该权限
//                        powerList.add(roleOrAccountPowerList.get(i).getPowerId());
//                    }
//                }
//            }
//        }
        return powerList;
    }
    /**
     * description: 角色权限明细查询
     * @author shiya.li@ucarinc.com
     * @date   2019/8/10 17:08
     * @params
     * @return
     */
   /* public ResultPage queryRolePowerList(QueryParam queryParam) {
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<RolePowerDto> list = powerDao.queryRolePowerList(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }*/

    @Override
    public ResultPage queryRolePowerList(RolePowerDto rolePowerDto) {
        Page<?> page = PageHelper.startPage(rolePowerDto.getPage(), rolePowerDto.getLimit());
        List<RolePowerDto> list = powerDao.queryRolePowerList(rolePowerDto);
        return new ResultPage(list, (int) page.getTotal(), rolePowerDto.getLimit(), rolePowerDto.getPage());
    }
    @Override
    public List<RolePowerDto> getRolePowerDtoList(RolePowerDto rolePowerDto){
        List<RolePowerDto> list = powerDao.queryRolePowerList(rolePowerDto);
        return list;
    }

}
