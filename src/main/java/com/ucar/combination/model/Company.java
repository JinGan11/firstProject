package com.ucar.combination.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.ucar.combination.model.dto.BaseBean;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * description:账户
 *
 * @author jianan.shu@ucarinc.com
 * @version 1.0
 * @date: 2019/8/3 8:56
 */
public class Company extends BaseBean {
	private Long companyId;
	private String companyName;
	private String creditCode;
	private Integer companyType;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
	private Date businessStartTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
	private Date businessDeadline;
	private String companyAddress;
	private String businessScope;
	private String legalPerson;
	private String registeredCapital;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
	private Date establishTime;
	private String registeredInstitution;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
	private Date causeTime;
	private String registeredStatus;
	private String liscensePath;
	private Integer companyNature;
	private String registrationAddress;
	private String bankName;
	private String bankAccount;
	private String telephone;
	private Integer companyMark;
	private Integer companyStatus;
	private Long createEmp;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	private Long modifyEmp;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date modifyTime;
	private String remark;
	private Integer status;
    private String oldCreditCode;

	public String getOldCreditCode() {
		return oldCreditCode;
	}

	public void setOldCreditCode(String oldCreditCode) {
		this.oldCreditCode = oldCreditCode;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}

	public Integer getCompanyType() {
		return companyType;
	}

	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
	}

	public Date getBusinessStartTime() {
		return businessStartTime;
	}

	public void setBusinessStartTime(Date businessStartTime) {
		this.businessStartTime = businessStartTime;
	}

	public Date getBusinessDeadline() {
		return businessDeadline;
	}

	public void setBusinessDeadline(Date businessDeadline) {
		this.businessDeadline = businessDeadline;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public Date getEstablishTime() {
		return establishTime;
	}

	public void setEstablishTime(Date establishTime) {
		this.establishTime = establishTime;
	}

	public String getRegisteredInstitution() {
		return registeredInstitution;
	}

	public void setRegisteredInstitution(String registeredInstitution) {
		this.registeredInstitution = registeredInstitution;
	}

	public Date getCauseTime() {
		return causeTime;
	}

	public void setCauseTime(Date causeTime) {
		this.causeTime = causeTime;
	}

	public String getRegisteredStatus() {
		return registeredStatus;
	}

	public void setRegisteredStatus(String registeredStatus) {
		this.registeredStatus = registeredStatus;
	}

	public String getLiscensePath() {
		return liscensePath;
	}

	public String getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(String registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public void setLiscensePath(String liscensePath) {
		this.liscensePath = liscensePath;
	}

	public Integer getCompanyNature() {
		return companyNature;
	}

	public void setCompanyNature(Integer companyNature) {
		this.companyNature = companyNature;
	}

	public String getRegistrationAddress() {
		return registrationAddress;
	}

	public void setRegistrationAddress(String registrationAddress) {
		this.registrationAddress = registrationAddress;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getCompanyMark() {
		return companyMark;
	}

	public void setCompanyMark(Integer companyMark) {
		this.companyMark = companyMark;
	}

	public Integer getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(Integer companyStatus) {
		this.companyStatus = companyStatus;
	}

	@Override
	public Long getCreateEmp() {
		return createEmp;
	}

	@Override
	public void setCreateEmp(Long createEmp) {
		this.createEmp = createEmp;
	}

	@Override
	public Date getCreateTime() {
		return createTime;
	}

	@Override
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public Long getModifyEmp() {
		return modifyEmp;
	}

	@Override
	public void setModifyEmp(Long modifyEmp) {
		this.modifyEmp = modifyEmp;
	}

	@Override
	public Date getModifyTime() {
		return modifyTime;
	}

	@Override
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
