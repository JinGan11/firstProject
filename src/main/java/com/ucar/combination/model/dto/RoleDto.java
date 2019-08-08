package com.ucar.combination.model.dto;

import java.security.PrivateKey;

/**
 * Description: 角色信息实体类
 *
 * @author gan.jin@ucarinc.com
 * @version 1.0
 * @Date 2019/8/6 10:53
 */
public class RoleDto extends BaseBean{
    private Long roleID;
    private String roleName;
    private String businessLine;
    private Integer roleStatus;
    private String accountNum;
    private String staffNum;
    private String staffName;
    private String departmentName;
    private String description;

    public Long getRoleID() {
        return roleID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setBusinessLine(String businessLine) {
        this.businessLine = businessLine;
    }

    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public void setStaffNum(String staffNum) {
        this.staffNum = staffNum;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getBusinessLine() {
        return businessLine;
    }

    public Integer getRoleStatus() {
        return roleStatus;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public String getStaffNum() {
        return staffNum;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getDescription() {
        return description;
    }
}
