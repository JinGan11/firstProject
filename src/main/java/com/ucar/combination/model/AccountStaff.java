package com.ucar.combination.model;

import com.ucar.combination.model.dto.BaseBean;

/**
 * description:账号员工实体类
 *
 * @Author junqiang.zhang@ucarinc.com
 * @Version 1.0
 * @Date: 2019/8/9 10:35
 **/
public class AccountStaff extends BaseBean {
    Long accountId;
    Long staffId;
    Long oldStaffId;
    String accountName;
    String password;
    int permissions;
    String secretEmail;
    String remark;
    String tree;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getOldStaffId() {
        return oldStaffId;
    }

    public void setOldStaffId(Long oldStaffId) {
        this.oldStaffId = oldStaffId;
    }

    public int getPermissions() {
        return permissions;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    public String getSecretEmail() {
        return secretEmail;
    }

    public void setSecretEmail(String secretEmail) {
        this.secretEmail = secretEmail;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTree() {
        return tree;
    }

    public void setTree(String tree) {
        this.tree = tree;
    }
}
