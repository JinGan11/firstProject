package com.ucar.combination.model;

import com.ucar.combination.model.dto.BaseBean;

import java.util.Date;

/**
 * @program: combination
 * @description: 员工表实体类
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-07-31 15:46
 */
public class Staff extends BaseBean {
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
    private Long accountId;

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

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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
