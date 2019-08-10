package com.ucar.combination.model.dto;

import java.util.Date;

/**
 * description:角色申请 角色 账户 关联表
 *
 * @author min.zhang08@ucarinc.com
 * @date: Create in 17:47 now
 **/
public class ApplyRoleAccountDto {
    private Long id;
    private Long  applyId;
    private Long roleId;
    private Long accountId;
    private Integer applyOperation;
    private Long createEmp;
    private Date createTime;
    private Long modifyEmp;
    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Integer getApplyOperation() {
        return applyOperation;
    }

    public void setApplyOperation(Integer applyOperation) {
        this.applyOperation = applyOperation;
    }

    public Long getCreateEmp() {
        return createEmp;
    }

    public void setCreateEmp(Long createEmp) {
        this.createEmp = createEmp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
