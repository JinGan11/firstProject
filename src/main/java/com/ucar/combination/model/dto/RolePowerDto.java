package com.ucar.combination.model.dto;

/**
 * @program: combination
 * @description: 角色权限
 * @author: shiya.li@ucarinc.com
 * @create: 2019-08-10 10:15
 */
public class RolePowerDto {
    private Long roleId;
    private String roleName;
    private String businessLine;
    private Integer roleStatus;
    private String approverAccountName;
    private String approverStaffNum;
    private String approverStaffName;
    private String approverDepartmentName;
    private String powerName;
    private Long powerId;
    private Integer page;
    private Integer limit;

    public Long getPowerId() {
        return powerId;
    }

    public void setPowerId(Long powerId) {
        this.powerId = powerId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getBusinessLine() {
        return businessLine;
    }

    public void setBusinessLine(String businessLine) {
        this.businessLine = businessLine;
    }

    public Integer getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }

    public String getApproverAccountName() {
        return approverAccountName;
    }

    public void setApproverAccountName(String approverAccountName) {
        this.approverAccountName = approverAccountName;
    }

    public String getApproverStaffNum() {
        return approverStaffNum;
    }

    public void setApproverStaffNum(String approverStaffNum) {
        this.approverStaffNum = approverStaffNum;
    }

    public String getApproverStaffName() {
        return approverStaffName;
    }

    public void setApproverStaffName(String approverStaffName) {
        this.approverStaffName = approverStaffName;
    }

    public String getApproverDepartmentName() {
        return approverDepartmentName;
    }

    public void setApproverDepartmentName(String approverDepartmentName) {
        this.approverDepartmentName = approverDepartmentName;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
