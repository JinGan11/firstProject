package com.ucar.combination.model;


import com.ucar.combination.model.dto.BaseBean;

import java.util.Date;

/**
 * description:账户
 *
 * @author jianan.shu@ucarinc.com
 * @version 1.0
 * @date: 2019/8/3 8:56
*/
public class Company extends BaseBean {
    private Long id;
    private String companyName;
    private String creditCode;
    private String companyType;
    private Date businessDeadline;
    private String companyAddress;
    private String businessScope;
    private String legalPerson;
    private Long registeredCapital;
    private Date establishTime;
    private String registeredInstitution;
    private Date issueDate;
    private String registeredStatus;
    private String liscensePath;
    private Integer companyNature;
    private String registrationAddress;
    private String bankName;
    private String bankAccount;
    private String telephone;
    private Integer companyMark;
    private Integer status;
    private String remark;

    public void setId(Long id) {
        this.id = id;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public void setBusinessDeadline(Date businessDeadline) {
        this.businessDeadline = businessDeadline;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public void setRegisteredCapital(Long registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public void setEstablishTime(Date establishTime) {
        this.establishTime = establishTime;
    }

    public void setRegisteredInstitution(String registeredInstitution) {
        this.registeredInstitution = registeredInstitution;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public void setRegisteredStatus(String registeredStatus) {
        this.registeredStatus = registeredStatus;
    }

    public void setLiscensePath(String liscensePath) {
        this.liscensePath = liscensePath;
    }

    public void setCompanyNature(Integer companyNature) {
        this.companyNature = companyNature;
    }

    public void setRegistrationAddress(String registrationAddress) {
        this.registrationAddress = registrationAddress;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setCompanyMark(Integer companyMark) {
        this.companyMark = companyMark;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public String getCompanyType() {
        return companyType;
    }

    public Date getBusinessDeadline() {
        return businessDeadline;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public Long getRegisteredCapital() {
        return registeredCapital;
    }

    public Date getEstablishTime() {
        return establishTime;
    }

    public String getRegisteredInstitution() {
        return registeredInstitution;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public String getRegisteredStatus() {
        return registeredStatus;
    }

    public String getLiscensePath() {
        return liscensePath;
    }

    public Integer getCompanyNature() {
        return companyNature;
    }

    public String getRegistrationAddress() {
        return registrationAddress;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getTelephone() {
        return telephone;
    }

    public Integer getCompanyMark() {
        return companyMark;
    }

    public Integer getStatus() {
        return status;
    }

    public Long getCreateEmp() {
        return createEmp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Long getModifyEmp() {
        return modifyEmp;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public String getRemark() {
        return remark;
    }
}
