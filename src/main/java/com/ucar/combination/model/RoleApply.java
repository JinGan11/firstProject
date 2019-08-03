package com.ucar.combination.model;

import java.util.Date;

/**
 * description:
 *
 * @author min.zhang08@ucarinc.com
 * @date: Create in 9:32 now
 **/
public class RoleApply {
    private Long id;
    private String applyNum;
    private Long roleId;
    private Integer applyStatus;
    private String applyStaffNum;
    private String applyStaffName;
    private Integer applyAccountId;
    private String applyAccountName;
    private Integer applyDepartmentId;
    private String applyDepartmentName;
    private Date applyTime;
    private String rejectReason;
    private Long modifyEmp;
    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(String applyNum) {
        this.applyNum = applyNum;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
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

    public Integer getApplyAccountId() {
        return applyAccountId;
    }

    public void setApplyAccountId(Integer applyAccountId) {
        this.applyAccountId = applyAccountId;
    }

    public String getApplyAccountName() {
        return applyAccountName;
    }

    public void setApplyAccountName(String applyAccountName) {
        this.applyAccountName = applyAccountName;
    }

    public Integer getApplyDepartmentId() {
        return applyDepartmentId;
    }

    public void setApplyDepartmentId(Integer applyDepartmentId) {
        this.applyDepartmentId = applyDepartmentId;
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

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
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
}
