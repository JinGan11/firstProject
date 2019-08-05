package com.ucar.combination.model;

import com.ucar.combination.model.dto.BaseBean;

/**
 * description:部门类
 *
 * @author 郑开添（kaitian.zheng@ucarinc.com）
 * @version 1.0
 * @date 2019/7/30 11:11
 **/
public class Department extends BaseBean {

    private Long id;
    private String departmentNo;
    private Integer workplace;
    private String departmentName;
    private Long staffId;
    private String staffName;
    private Integer cityId;
    private String address;
    private String telephone;
    private String email;
    private String landline;
    private Integer level;
    private String upperDepartmentNo;
    private String supportBusiness;
    private Integer departmentType;
    private Integer status;
    private String remark;
    private String longitude;
    private String latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    public Integer getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Integer workplace) {
        this.workplace = workplace;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getStaffId() {
        return staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getUpperDepartmentNo() {
        return upperDepartmentNo;
    }

    public void setUpperDepartmentNo(String upperDepartmentNo) {
        this.upperDepartmentNo = upperDepartmentNo;
    }

    public String getSupportBusiness() {
        return supportBusiness;
    }

    public void setSupportBusiness(String supportBusiness) {
        this.supportBusiness = supportBusiness;
    }

    public Integer getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(Integer departmentType) {
        this.departmentType = departmentType;
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
