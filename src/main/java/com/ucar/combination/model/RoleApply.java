package com.ucar.combination.model;

import java.util.Date;

/**
 * description:
 *
 * @author min.zhang08@ucarinc.com
 * @date: Create in 9:32 now
 **/
public class RoleApply {
    private Long roleApplyId;
    private Long roleId;
    private Integer applyStatus;
    private Long applyAccountId;
    private Date applyTime;
    private Long modifyEmp;
    private Date modifyTime;
    private String rejectReason;

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

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public Long getApplyAccountId() {
        return applyAccountId;
    }

    public void setApplyAccountId(Long applyAccountId) {
        this.applyAccountId = applyAccountId;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
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
