package com.ucar.combination.model.dto;

/**
 * description:角色申请 修改页面进来的  账户列表
 *
 * @author min.zhang08@ucarinc.com
 * @date: Create in 10:23 now
 **/
public class AccountListByApplyIdDto {
    private Long id;
    private String accountName;//申请账号
    private String staffName;//关联员工姓名
    private String staffNum;//关联员工编号
    private String staffDepartmentName;//关联员工所属部门
    private Integer applyOperation;

    private Long applyId;//申请编号id
    private Long roleId;
    private Long  accountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(String staffNum) {
        this.staffNum = staffNum;
    }

    public String getStaffDepartmentName() {
        return staffDepartmentName;
    }

    public void setStaffDepartmentName(String staffDepartmentName) {
        this.staffDepartmentName = staffDepartmentName;
    }

    public Integer getApplyOperation() {
        return applyOperation;
    }

    public void setApplyOperation(Integer applyOperation) {
        this.applyOperation = applyOperation;
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
}
