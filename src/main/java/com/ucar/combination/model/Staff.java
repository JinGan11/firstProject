package com.ucar.combination.model;

import java.util.Date;

/**
 * @program: combination
 * @description: 员工表实体类
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-07-31 15:46
 */
public class Staff {
    private long id;
    private String staffNum;
    private String staffName;
    private int staffSex;
    private String staffTelephone;
    private String staffEmail;
    private long departmentId;
    private String departmentName;
    private int isDimission;
    private int status;
    private Date createTime;
    private long createEmp;
    private Date modifyTime;
    private long modifyEmp;
    private String remark;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int getStaffSex() {
        return staffSex;
    }

    public void setStaffSex(int staffSex) {
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

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getIsDimission() {
        return isDimission;
    }

    public void setIsDimission(int isDimission) {
        this.isDimission = isDimission;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getCreateEmp() {
        return createEmp;
    }

    public void setCreateEmp(long createEmp) {
        this.createEmp = createEmp;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public long getModifyEmp() {
        return modifyEmp;
    }

    public void setModifyEmp(long modifyEmp) {
        this.modifyEmp = modifyEmp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
