package com.ucar.combination.model.dto;
/**
 * description:员工管理其他信息
 *
 * @author jianan.shu@ucarinc.com
 * @version 1.0
 * @date: 2019/8/14 16:31
*/
public class StaffAccountDTO {
    private String createEmpAccountName;
    private String createEmpStaffName;
    private String modifyEmpAccountName;
    private String modifyEmpStaffName;

    public String getCreateEmpAccountName() {
        return createEmpAccountName;
    }

    public void setCreateEmpAccountName(String createEmpAccountName) {
        this.createEmpAccountName = createEmpAccountName;
    }

    public String getCreateEmpStaffName() {
        return createEmpStaffName;
    }

    public void setCreateEmpStaffName(String createEmpStaffName) {
        this.createEmpStaffName = createEmpStaffName;
    }

    public String getModifyEmpAccountName() {
        return modifyEmpAccountName;
    }

    public void setModifyEmpAccountName(String modifyEmpAccountName) {
        this.modifyEmpAccountName = modifyEmpAccountName;
    }

    public String getModifyEmpStaffName() {
        return modifyEmpStaffName;
    }

    public void setModifyEmpStaffName(String modifyEmpStaffName) {
        this.modifyEmpStaffName = modifyEmpStaffName;
    }
}
