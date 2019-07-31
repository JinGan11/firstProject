package com.ucar.combination.model;

import java.util.Date;

/**
 * @program: combination
 * @description: 用户账号信息实体类
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-07-30 16:37
 */
public class User {
    private long id;
    private String accountName;
    private String accountPassword;
    private long staffId;
    private int permissions;
    private String accountSecretEmail;
    private Date createTime;
    private long createEmp;
    private Date modifyTime;
    private long modifyEmp;
    private int accountState;
    private String accountRemark;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getStaffId() {
        return staffId;
    }

    public void setStaffId(long staffId) {
        this.staffId = staffId;
    }

    public int getPermissions() {
        return permissions;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    public String getAccountSecretEmail() {
        return accountSecretEmail;
    }

    public void setAccountSecretEmail(String accountSecretEmail) {
        this.accountSecretEmail = accountSecretEmail;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getCreateEmp() {
        return createEmp;
    }

    public void setCreateEmp(long createEmp) {
        this.createEmp = createEmp;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public long getModifyEmp() {
        return modifyEmp;
    }

    public void setModifyEmp(long modifyEmp) {
        this.modifyEmp = modifyEmp;
    }

    public int getAccountState() {
        return accountState;
    }

    public void setAccountState(int accountState) {
        this.accountState = accountState;
    }

    public String getAccountRemark() {
        return accountRemark;
    }

    public void setAccountRemark(String accountRemark) {
        this.accountRemark = accountRemark;
    }
}
