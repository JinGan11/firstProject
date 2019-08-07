package com.ucar.combination.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.RoleManagementDao;
import com.ucar.combination.model.Role;
import com.ucar.combination.model.dto.RoleDto;
import com.ucar.combination.service.RoleManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public ResultPage queryList(QueryParam queryParam) {
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<Map<String, Object>> list = roleManagementDao.queryList(queryParam);
        System.out.println(list);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }

    @Override
    public void updateStatus(int id){
        roleManagementDao.updateStatus(id);
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
    public ResultPage getOwnedRoleList() {
        ResultPage resultPage = new ResultPage();
        List<Role> list = roleManagementDao.getAccountRoleListById(2L);
        resultPage.setList(list);
        return resultPage;
    }

    @Override
    public ResultPage getnotOwnedRoleList() {
        ResultPage resultPage1 = getRoleList();
        ResultPage resultPage2 = getOwnedRoleList();
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

    @Override
    public RoleDto getOneInf(int id) {
        RoleDto roleDto = roleManagementDao.getOneInf(id);
        return roleDto;
    }

    @Override
    public void updateByModify(RoleDto role) {
       roleManagementDao.updateByModify(role);
    }
}
