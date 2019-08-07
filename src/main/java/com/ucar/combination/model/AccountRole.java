package com.ucar.combination.model;

import com.ucar.combination.model.dto.BaseBean;

/**
 * @version 1.0
 * @description: 账户角色实体类
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-08-06 15:58
 */
public class AccountRole extends BaseBean {
    private Long id;
    private Long accountId;
    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
