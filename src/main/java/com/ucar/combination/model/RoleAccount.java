package com.ucar.combination.model;

public class RoleAccount {
    private Long roleId;
    private Long accountId;
    private String roleName;
    private String businessLine;
    private String accountName;
    private String staffNum;
    private String staffName;
    private String departmentName;
    private Byte roleStatus;
    private String accountState;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getRoleId() { return roleId; }

    public void setRoleId(Long roleId) { this.roleId = roleId; }

    public String getRoleName() {
        return roleName;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getStaffNum() { return staffNum; }

    public void setStaffNum(String staffNum) { this.staffNum = staffNum; }

    public String getStaffName() {
        return staffName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public Byte getRoleStatus() {
        return roleStatus;
    }

    public String getAccountState() {
        return accountState;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getBusinessLine() { return businessLine; }

    public void setBusinessLine(String businessLine) { this.businessLine = businessLine; }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setRoleStatus(Byte roleStatus) {
        this.roleStatus = roleStatus;
    }

    public void setAccountState(String accountState) {
        this.accountState = accountState;
    }
}
