package com.ucar.combination.model.dto;

/**
 * description:角色申请 获取当前登陆账号的 员工 部门
 *
 * @author min.zhang08@ucarinc.com
 * @date: Create in 20:32 now
 **/
public class LoginInfoInRoleApplyDto {
    private  String applyAccountName;
    private  String applyStaffNum;
    private  String applyStaffName;
    private  String applyDepartmentName;
    private String modifyStaffName;

    public String getModifyStaffName() {
        return modifyStaffName;
    }

    public void setModifyStaffName(String modifyStaffName) {
        this.modifyStaffName = modifyStaffName;
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
}
