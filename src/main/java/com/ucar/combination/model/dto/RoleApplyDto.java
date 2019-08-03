package com.ucar.combination.model.dto;

import java.util.Date;

/**
 * description:角色申请列表对应实体
 *
 * @author min.zhang08@ucarinc.com
 * @date: Create in 9:30 now
 **/
public class RoleApplyDto {
    private Long id;
    private String roleApplyNum;
    private Long roleId;
    private String roleName;
    private String approverStaffName;
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

    public RoleApplyDto(Long id, String roleApplyNum, Long roleId, String roleName, String approverStaffName, Integer businessLine, String applyAccountName, String applyStaffNum, String applyStaffName, String applyDepartmentName, Date applyTime, Integer applyStatus, Long modifyEmp, Date modifyTime, String rejectReason) {
        this.id = id;
        this.roleApplyNum = roleApplyNum;
        this.roleId = roleId;
        this.roleName = roleName;
        this.approverStaffName = approverStaffName;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleApplyNum() {
        return roleApplyNum;
    }

    public void setRoleApplyNum(String roleApplyNum) {
        this.roleApplyNum = roleApplyNum;
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

    public String getApproverStaffName() {
        return approverStaffName;
    }

    public void setApproverStaffName(String approverStaffName) {
        this.approverStaffName = approverStaffName;
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
