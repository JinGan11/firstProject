package com.ucar.combination.model;

import java.util.List;

/**
 * @version 1.0
 * @description: 分配权限
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-08-07 14:18
 */
public class AssignPermission {
    private Long roleInfoId;
    private List<Power> powerList;

    public Long getRoleInfoId() {
        return roleInfoId;
    }

    public void setRoleInfoId(Long roleInfoId) {
        this.roleInfoId = roleInfoId;
    }

    public List<Power> getPowerList() {
        return powerList;
    }

    public void setPowerList(List<Power> powerList) {
        this.powerList = powerList;
    }
}
