package com.ucar.combination.model;

/**
 * @version 1.0
 * @description: 角色权限类
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-08-07 14:12
 */
public class RolePower {
    private Long id;
    private Long roleInfoId;
    private Long powerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleInfoId() {
        return roleInfoId;
    }

    public void setRoleInfoId(Long roleInfoId) {
        this.roleInfoId = roleInfoId;
    }

    public Long getPowerId() {
        return powerId;
    }

    public void setPowerId(Long powerId) {
        this.powerId = powerId;
    }
}
