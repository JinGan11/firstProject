package com.ucar.combination.model;

public class RoleAccount {
    private Integer Id;
    private String roleName;
    private String businessLine;
    private String accountName;
    private Integer staffNum;
    private String staffName;
    private String departmentName;
    private Byte roleStatus;
    private String accountState;

    public Integer getId() {
        return Id;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getAccountName() {
        return accountName;
    }

    public Integer getStaffNum() {
        return staffNum;
    }

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

    public void setId(Integer id) {
        Id = id;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getBusinessLine() { return businessLine; }

    public void setBusinessLine(String businessLine) { this.businessLine = businessLine; }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setStaffNum(Integer staffNum) {
        this.staffNum = staffNum;
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
