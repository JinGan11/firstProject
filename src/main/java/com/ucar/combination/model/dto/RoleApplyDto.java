package com.ucar.combination.model.dto;

import java.util.Date;

/**
 * description:角色申请列表对应实体
 *
 * @author min.zhang08@ucarinc.com
 * @date: Create in 9:30 now
 **/
public class RoleApplyDto {
    private Long roleApplyId;
    private Long roleId;
    private String roleName;
    private Long approverEmp;
    private Integer businessLine;
    private String applyAccountName;
    private String applyStaffNum;
    private String applyStaffName;
    private String applyDepartmentName;
    private Date applyTime;
    private Integer applyStatus;
    private Long modifyEmp;
    private Date modifyTime;
    private String rejectReason;

    public RoleApplyDto(Long roleApplyId, Long roleId, String roleName, Long approverEmp, Integer businessLine, String applyAccountName, String applyStaffNum, String applyStaffName, String applyDepartmentName, Date applyTime, Integer applyStatus, Long modifyEmp, Date modifyTime, String rejectReason) {
        this.roleApplyId = roleApplyId;
        this.roleId = roleId;
        this.roleName = roleName;
        this.approverEmp = approverEmp;
        this.businessLine = businessLine;
        this.applyAccountName = applyAccountName;
        this.applyStaffNum = applyStaffNum;
        this.applyStaffName = applyStaffName;
        this.applyDepartmentName = applyDepartmentName;
        this.applyTime = applyTime;
        this.applyStatus = applyStatus;
        this.modifyEmp = modifyEmp;
        this.modifyTime = modifyTime;
        this.rejectReason = rejectReason;
    }

    public Long getRoleApplyId() {
        return roleApplyId;
    }

    public void setRoleApplyId(Long roleApplyId) {
        this.roleApplyId = roleApplyId;
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

    public Long getApproverEmp() {
        return approverEmp;
    }

    public void setApproverEmp(Long approverEmp) {
        this.approverEmp = approverEmp;
    }

    public Integer getBusinessLine() {
        return businessLine;
    }

    public void setBusinessLine(Integer businessLine) {
        this.businessLine = businessLine;
    }

    public String getApplyAccountName() {
        return applyAccountName;
    }

    public void setApplyAccountName(String applyAccountName) {
        this.applyAccountName = applyAccountName;
    }

    public String getApplyStaffNum() {
        return applyStaffNum;
    }

    public void setApplyStaffNum(String applyStaffNum) {
        this.applyStaffNum = applyStaffNum;
    }

    public String getApplyStaffName() {
        return applyStaffName;
    }

    public void setApplyStaffName(String applyStaffName) {
        this.applyStaffName = applyStaffName;
    }

    public String getApplyDepartmentName() {
        return applyDepartmentName;
    }

    public void setApplyDepartmentName(String applyDepartmentName) {
        this.applyDepartmentName = applyDepartmentName;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public Long getModifyEmp() {
        return modifyEmp;
    }

    public void setModifyEmp(Long modifyEmp) {
        this.modifyEmp = modifyEmp;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
