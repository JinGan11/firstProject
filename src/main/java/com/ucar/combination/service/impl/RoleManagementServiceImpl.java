package com.ucar.combination.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.RoleManagementDao;
import com.ucar.combination.model.*;
import com.ucar.combination.model.dto.RoleAccountStaffDto;
import com.ucar.combination.model.dto.RoleDto;
import com.ucar.combination.service.RoleManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Description: 角色管理
 *
 * @author gan.jin@ucarinc.com
 * @version 1.0
 * @Date 2019/7/31 16:55
 */
@Service
public class RoleManagementServiceImpl implements RoleManagementService {
    @Autowired
    private RoleManagementDao roleManagementDao;

    /**
     * description:取出数据库中的列表数据
     * @author gan.jin@ucarinc.com
     * @date 2019/8/7 15:25
     * @param queryParam
     * @return com.ucar.combination.common.ResultPage
     */

    @Override
    public ResultPage queryList(QueryParam queryParam) {
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<Map<String, Object>> list = roleManagementDao.queryList(queryParam);
        System.out.println(list);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }


    /**
     * description:依据传入参数主键id逻辑删除
     * @author gan.jin@ucarinc.com
     * @date 2019/8/7 15:26
     * @param id  传入参数主键id
     * @return void
     */

    @Override
    public void updateStatus(int id){
        roleManagementDao.updateStatus(id);
    }

    @Override
    public Role judgeExist(String roleName) {
        Role result = roleManagementDao.judgeExist(roleName);
        return result;
    }

    /**
     * description: 获取所有角色数据列表
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/5 10:42
     * @params 无
     * @return 角色列表结果集
     */
    @Override
    public ResultPage getRoleList() {
        ResultPage resultPage = new ResultPage();
        List<Role> list = roleManagementDao.getRoleList();
        resultPage.setList(list);
        return resultPage;
    }

    /**
     * description: 为账户插入新添加的角色
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/7 10:20
     * @params role 角色
     * @return
     */
    @Override
    public void insertRole(RoleDto role) {
        roleManagementDao.insertRole(role);
    }

    /**
     * description: 获取账户已拥有的角色
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/7 10:20
     * @params
     * @return
     */
    @Override
    public ResultPage getOwnedRoleList(Account account) {
        ResultPage resultPage = new ResultPage();
        List<Role> list = roleManagementDao.getAccountRoleListById(account.getId());
        resultPage.setList(list);
        return resultPage;
    }

    @Override
    public ResultPage getnotOwnedRoleList(Account account) {
        ResultPage resultPage1 = getRoleList();
        ResultPage resultPage2 = getOwnedRoleList(account);
        ResultPage resultPage3 = new ResultPage();
        List<Role> list = (List<Role>) resultPage1.getList();
        List<Role> ownedList = (List<Role>) resultPage2.getList();
        //移除已拥有的角色
        Iterator<Role> it = list.iterator();
        while(it.hasNext()){
            Long roleId = it.next().getRoleId();
            for (int i = 0; i < ownedList.size(); i++) {
                if(roleId.equals(ownedList.get(i).getRoleId())){
                    it.remove();
                }
            }
        }
        resultPage3.setList(list);
        return resultPage3;
    }

    /**
     * description:将选中的数据取出
     * @author gan.jin@ucarinc.com
     * @date 2019/8/7 15:28
     * @param id 主键id
     * @return com.ucar.combination.model.dto.RoleDto 数据对象
     */

    @Override
    public RoleDto getOneInf(int id) {
        RoleDto roleDto = roleManagementDao.getOneInf(id);
        return roleDto;
    }

    @Override
    public RoleAccountStaffDto getOtherOneInf(int id) {
        RoleAccountStaffDto roleAccountStaffDto = roleManagementDao.getOtherOneInf(id);
        return roleAccountStaffDto;
    }

    /**
     * description:修改数据
     * @author gan.jin@ucarinc.com
     * @date 2019/8/7 15:29
     * @param role 传入修改的数据
     * @return void
     */

    @Override
    public void updateByModify(RoleDto role) {
        roleManagementDao.updateByModify(role);
    }

    /**
     * description: 为角色分配权限
     * @author peng.zhang11@ucarinc.com
     * @date 2019/8/6 12:03
     * @params powerList 权限列表
     * @return
     */
    @Override
    public Result assignPermission(AssignPermission assignPermission, Long accountId) {
        //  1.查询该账户已拥有的特殊权限，如果powerList中有而数据库中没有，则表示删除该权限
        //  2.然后插入powerList中有的，而数据库中没有的
        List<RolePower> list = roleManagementDao.getRolePowerListById(assignPermission.getRoleInfoId());
        RolePower rolePower = new RolePower();
        // 如果该角色没有权限，则全部插入
        if (list == null || list.size() == 0) {
            for (int i = 0; i < assignPermission.getPowerList().size(); i++) {
                //插入该权限
                rolePower.setRoleInfoId(assignPermission.getRoleInfoId());
                rolePower.setPowerId(assignPermission.getPowerList().get(i).getPowerId());
                roleManagementDao.insertRolePower(rolePower);
            }
        } else {
            // 删除角色被移除的权限
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < assignPermission.getPowerList().size(); j++) {
                    if (list.get(i).getPowerId().equals(assignPermission.getPowerList().get(j).getPowerId())) {
                        break;
                    }
                    if (!list.get(i).getPowerId().equals(assignPermission.getPowerList().get(j).getPowerId())
                            && j == assignPermission.getPowerList().size() -1) {
                        //删除该角色
                        rolePower.setRoleInfoId(assignPermission.getRoleInfoId());
                        rolePower.setPowerId(list.get(i).getPowerId());
                        roleManagementDao.removeRolePowerById(rolePower);
                    }
                }
            }
            // 为角色插入新添加的权限
            for (int i = 0; i < assignPermission.getPowerList().size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    if (assignPermission.getPowerList().get(i).getPowerId().equals(list.get(j).getPowerId())) {
                        break;
                    }
                    if (!assignPermission.getPowerList().get(i).getPowerId().equals(list.get(j).getPowerId())
                            && j == list.size() -1) {
                        //插入该权限
                        rolePower.setRoleInfoId(assignPermission.getRoleInfoId());
                        rolePower.setPowerId(assignPermission.getPowerList().get(i).getPowerId());
                        roleManagementDao.insertRolePower(rolePower);
                    }
                }
            }
        }
        return null;
    }

    /**
     * description: 获取角色已有的权限（转换成了ArrayList形式）
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/8 13:38
     * @params rolePower 角色信息
     * @return
     */
    @Override
    public Result getRolePower(RolePower rolePower) {
        List<RolePower> list = roleManagementDao.getRolePowerListById(rolePower.getRoleInfoId());
        if (list.size() == 0 || list == null){
            return Result.ok().put("rolePowerList", null);
        } else {
            List rolePowerList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                rolePowerList.add(list.get(i).getPowerId());
            }
            return Result.ok().put("rolePowerList", rolePowerList);
        }
    }


    /**
     * description: 获取角色拥有的权限
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/8 13:40
     * @params
     * @return
     */
    @Override
    public List<RolePower> getRolePowerList(Long roleInfoId) {
        List<RolePower> list = roleManagementDao.getRolePowerListById(roleInfoId);
        return list;
    }

    /**
     * description: 获取所有的ROLE信息
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/8 17:44
     * @params
     * @return
     */
    @Override
    public List<Role> queryroleList() {
        return roleManagementDao.queryroleList();
    }

    /**
     * description: 获取角色账户信息
     * @author qingyu.lan@ucarinc.com
     * @date   2019/8/12 11:06
     * @return
     */
    @Override
    public ResultPage getRoleAccountList(QueryParam queryParam){
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<Map<String,Object>> list = roleManagementDao.getRoleAccountList(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }

    @Override
    public void removeRoleAccount(Map<String, Object> map){
        roleManagementDao.removeRoleAccount(map);
    }

    @Override
    public void addRoleAccount(Map<String, Object> map){
        roleManagementDao.addRoleAccount(map);
    }
}
