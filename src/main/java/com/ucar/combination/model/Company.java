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
    private Integer companyType;
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
    private Integer companyStatus;
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(Long registeredCapital) {
        this.registeredCapital = registeredCapital;
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

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
