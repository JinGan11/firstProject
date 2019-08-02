package com.ucar.combination.model.dto;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * @program: combination
 * @description: 查询部门的实体类
 * @author: jing.luo01@ucarinc.com
 * @create: 2019-08-01 17:33
 */
public class SearchDepartmentDto {
	private BigInteger id;
	private String departmentNo;
	private Integer workplace;
	private String departmentName;
	private BigInteger staffId;
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
	private BigInteger createEmp;
	private Timestamp createTime;
	private BigInteger modifyEmp;
	private Timestamp modifyTime;
	private String remark;
	private String cityName;
	private String companyName;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
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

	public BigInteger getStaffId() {
		return staffId;
	}

	public void setStaffId(BigInteger staffId) {
		this.staffId = staffId;
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

	public BigInteger getCreateEmp() {
		return createEmp;
	}

	public void setCreateEmp(BigInteger createEmp) {
		this.createEmp = createEmp;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public BigInteger getModifyEmp() {
		return modifyEmp;
	}

	public void setModifyEmp(BigInteger modifyEmp) {
		this.modifyEmp = modifyEmp;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
