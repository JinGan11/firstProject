package com.ucar.combination.model;

import com.ucar.combination.model.dto.BaseBean;

import java.util.Date;

/**
 * @program: combination
 * @description: 用户账号信息实体类
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-07-30 16:37
 */
public class User extends BaseBean {
    private Long id;
    private String accountName;
    private String accountPassword;
    private Long staffId;
    private Integer permissions;
    private String accountSecretEmail;
    private Integer accountState;
    private String accountRemark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Integer getPermissions() {
        return permissions;
    }

    public void setPermissions(Integer permissions) {
        this.permissions = permissions;
    }

    public String getAccountSecretEmail() {
        return accountSecretEmail;
    }

    public void setAccountSecretEmail(String accountSecretEmail) {
        this.accountSecretEmail = accountSecretEmail;
    }

    public Integer getAccountState() {
        return accountState;
    }

    public void setAccountState(Integer accountState) {
        this.accountState = accountState;
    }

    public String getAccountRemark() {
        return accountRemark;
    }

    public void setAccountRemark(String accountRemark) {
        this.accountRemark = accountRemark;
    }
}
