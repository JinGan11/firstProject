package com.ucar.combination.model;

/**
 * Description: 角色实体类
 *
 * @author gan.jin@ucarinc.com
 * @version 1.0
 * @Date 2019/8/2 14:44
 */
public class Role {

    private long roleId;
    private String roleName;
    private String accountNum;
    private long staffNum;
    private String staffName;
    private String departmentName;
    private Integer roleStatus;
    private String description;

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public void setStaffNum(long staffNum) {
        this.staffNum = staffNum;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public long getStaffNum() {
        return staffNum;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public Integer getRoleStatus() {
        return roleStatus;
    }

    public String getDescription() {
        return description;
    }
}
