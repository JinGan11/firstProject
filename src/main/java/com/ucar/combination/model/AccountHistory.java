package com.ucar.combination.model;

import java.util.Date;

public class AccountHistory {
    private Long id;

    private Long accountId;

    private String accountName;

    private String historyOperationType;

    private Date createTime;

    private Long createEmp;

    private String staffName;

    private String staffNum;

    private Byte permissions;

    private Byte accountState;

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

    public String getHistoryOperationType() {
        return historyOperationType;
    }

    public void setHistoryOperationType(String historyOperationType) {
        this.historyOperationType = historyOperationType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateEmp() {
        return createEmp;
    }

    public void setCreateEmp(Long createEmp) {
        this.createEmp = createEmp;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(String staffNum) {
        this.staffNum = staffNum;
    }

    public Byte getPermissions() {
        return permissions;
    }

    public void setPermissions(Byte permissions) {
        this.permissions = permissions;
    }

    public Byte getAccountState() {
        return accountState;
    }

    public void setAccountState(Byte accountState) {
        this.accountState = accountState;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}