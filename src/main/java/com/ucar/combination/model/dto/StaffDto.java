package com.ucar.combination.model.dto;

/**
 * description:
 *
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-31 13:45:06
 **/
public class StaffDto extends BaseBean {
    private Long id;
    private String staffNum;
    private String staffName;
    private Integer staffSex;
    private String staffTelephone;
    private String staffEmail;
    private Long departmentId;
    private String departmentName;
    private Integer isDimission;
    private Integer status;
    private String remark;

    private String accountName;
    private Long accountId;
    private String upperDepartmentNo;
    private String upperDepartmentName;

    private String createName;
    private String modifyName;



    public StaffDto() {
    }

    public StaffDto(Long id, String staffNum, String staffName, Integer staffSex, String staffTelephone, String staffEmail, Long departmentId, String departmentName, Integer isDimission, Integer status, String remark, String accountName, Long accountId, String upperDepartmentNo, String upperDepartmentName) {
        this.id = id;
        this.staffNum = staffNum;
        this.staffName = staffName;
        this.staffSex = staffSex;
        this.staffTelephone = staffTelephone;
        this.staffEmail = staffEmail;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.isDimission = isDimission;
        this.status = status;
        this.remark = remark;
        this.accountName = accountName;
        this.accountId = accountId;
        this.upperDepartmentNo = upperDepartmentNo;
        this.upperDepartmentName = upperDepartmentName;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getModifyName() {
        return modifyName;
    }

    public void setModifyName(String modifyName) {
        this.modifyName = modifyName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getUpperDepartmentName() {
        return upperDepartmentName;
    }

    public void setUpperDepartmentName(String upperDepartmentName) {
        this.upperDepartmentName = upperDepartmentName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getUpperDepartmentNo() {
        return upperDepartmentNo;
    }

    public void setUpperDepartmentNo(String upperDepartmentNo) {
        this.upperDepartmentNo = upperDepartmentNo;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(String staffNum) {
        this.staffNum = staffNum;
    }

    public Integer getStaffSex() {
        return staffSex;
    }

    public void setStaffSex(Integer staffSex) {
        this.staffSex = staffSex;
    }

    public String getStaffTelephone() {
        return staffTelephone;
    }

    public void setStaffTelephone(String staffTelephone) {
        this.staffTelephone = staffTelephone;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getIsDimission() {
        return isDimission;
    }

    public void setIsDimission(Integer isDimission) {
        this.isDimission = isDimission;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
