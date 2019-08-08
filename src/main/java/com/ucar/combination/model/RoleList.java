package com.ucar.combination.model;

import java.util.List;

/**
 * @version 1.0
 * @description: 封装角色列表
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-08-06 10:57
 */
public class RoleList {
    private Long id;
    private List<Role> roleList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
