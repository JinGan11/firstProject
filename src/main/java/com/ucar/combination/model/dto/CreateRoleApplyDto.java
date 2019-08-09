package com.ucar.combination.model.dto;

import com.ucar.combination.model.Account;

import java.util.Date;
import java.util.List;

/**
 * description:
 *
 * @author min.zhang08@ucarinc.com
 * @date: Create in 15:03 now
 **/
public class CreateRoleApplyDto {
    private Long id; //角色申请表自增主键
    private String roleApplyNum;//申请编号
    private Long roleId;
    private String roleName;//角色名字
    private String applyAccountName;//申请人账号
    private String applyStaffNum;//申请人员工编号
    private String applyStaffName;//申请人员工姓名
    private String applyDepartmentName;//申请人部门名字
    private Integer applyStatus;//申请状态
    private Date applyTime;//申请时间

    public String getModifyStaffName() {
        return modifyStaffName;
    }

    public void setModifyStaffName(String modifyStaffName) {
        this.modifyStaffName = modifyStaffName;
    }

    private String modifyStaffName;//修改人
    private Date modifyTime;//修改时间
    private List<Object> accountIdList;

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

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }



    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public List<Object> getAccountIdList() {
        return accountIdList;
    }

    public void setAccountIdList(List<Object> accountIdList) {
        this.accountIdList = accountIdList;
    }
}
