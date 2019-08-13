package com.ucar.combination.model.dto;


import java.util.Date;

/**
 * @program: combination
 * @description: 账户权限数据传输BEAN
 * @author: jing.luo01@ucarinc.com
 * @create: 2019-08-09 15:15
 */
public class AccountPowerDto {
	private String accountName;
	private Long staffId;
	private String staffNum;
	private String staffName;
	private String departmentName;
	private String powerName;
	private Integer accountState;
	private Long createEmp;
	private Date createTime;
	private Long modifyEmp;
	private Date modifyTime;
	private String remark;
	private String email;
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
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

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPowerName() {
		return powerName;
	}

	public void setPowerName(String powerName) {
		this.powerName = powerName;
	}

	public Integer getAccountState() {
		return accountState;
	}

	public void setAccountState(Integer accountState) {
		this.accountState = accountState;
	}
}
