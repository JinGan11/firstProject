package com.ucar.combination.model;

import java.util.List;

/**
 * @version 1.0
 * @description: 封装权限列表
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-08-06 10:56
 */
public class PowerList {
    private Long id;
    private List<Power> powerList;
    private List<Role> roleList;

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Power> getPowerList() {
        return powerList;
    }

    public void setPowerList(List<Power> powerList) {
        this.powerList = powerList;
    }
}
