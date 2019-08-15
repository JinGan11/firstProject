package com.ucar.combination.dao;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.model.AccountRole;
import com.ucar.combination.model.Role;

import com.ucar.combination.model.RolePower;
import com.ucar.combination.model.dto.RoleAccountStaffDto;
import com.ucar.combination.model.dto.RoleDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Description: 角色管理
 *
 * @author gan.jin@ucarinc.com
 * @version 1.0
 * @Date 2019/7/31 16:57
 */
@Component
public interface RoleManagementDao {
    List<Map<String, Object>> queryList(Map<String, Object> map);
    List<Map<String, Object>> queryEffectiveList(Map<String, Object> map);

    /**
     * description: 获取所有角色数据
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/5 10:42
     * @params
     * @return 所有角色数据列表
     */
    List<Role> getRoleList();

    /**
     * description:依据主键ID逻辑删除数据
     * @author gan.jin@ucarinc.com
     * @date 2019/8/10 15:35
     * @param id 主键
     * @return
     */
    void updateStatus(int id);

    /**
     * description:插入新的角色信息数据到数据库
     * @author gan.jin@ucarinc.com
     * @date 2019/8/10 15:36
     * @param role RoDto类型
     * @return
     */
    void insertRole(RoleDto role);

    /**
     * description:用于判断角色名称是否存在
     * @author gan.jin@ucarinc.com
     * @date 2019/8/12 11:14
     * @param roleName 角色名称
     * @return 角色信息实体类
     */

    Role judgeExist(String roleName);


    /**
     * description: 通过账户id获取账户拥有的角色
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/6 15:47
     * @params accountId 账户ID
     * @return
     */
    List<Role> getAccountRoleListById(Long accountId);

    /**
     * description: 为账户移除角色
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/6 16:24
     * @params accountRole 账户角色
     * @return
     */
    void removeAccountRoleById(AccountRole accountRole);

    /**
     * description: 移除账号的所有角色
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/13 10:24
     * @params accountRole 描述

     * @return
     */
    void removeAllAccountRoleById(Long accountId);

    /**
     * description: 为账户添加新角色
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/6 16:24
     * @params accountRole 账户角色
     * @return
     */
    void insertAccountRole(AccountRole accountRole);
    RoleDto getOneInf(int id);
    RoleAccountStaffDto getOtherOneInf(int id);
    void updateByModify(RoleDto role);

    /**
     * description: 获取角色已有的权限
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/7 15:03
     * @params roleId 描述

     * @return
     */
    List<RolePower> getRolePowerListById(Long roleId);

    /**
     * description: 插入权限到角色权限表
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/7 14:37
     * @params rolePower 角色权限
     * @return
     */
    void insertRolePower(RolePower rolePower);

    /**
     * description: 删除角色的权限
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/7 14:38
     * @params rolePower 角色权限
     * @return
     */
    void removeRolePowerById(RolePower rolePower);

    /**
     * description: 选择所有角色信息
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/8 17:46
     * @params
     * @return
     */
    List<Role> queryroleList();

    /**
     * description: 获取角色账户信息
     * @author qingyu.lan@ucarinc.com
     * @date   2019/8/12 11:06
     * @return
     */
    List<Map<String,Object>> getRoleAccountList(QueryParam queryParam);

    /**
     * description: 删除角色账户信息
     * @author qingyu.lan@ucarinc.com
     * @date   2019/8/12 13:06
     * @param map
     */
    void removeRoleAccount(Map<String, Object> map);

    /**
     * description: 添加角色账户信息
     * @author qingyu.lan@ucarinc.com
     * @date   2019/8/12 13:06
     * @param map
     */
    void addRoleAccount(Map<String, Object> map);
}
