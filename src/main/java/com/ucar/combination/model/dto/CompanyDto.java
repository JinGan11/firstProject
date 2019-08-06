package com.ucar.combination.model.dto;

import java.util.Date;
/**
 * description:公司查询类
 *
 * @author jianan.shu@ucarinc.com
 * @version 1.0
 * @date: 2019/8/6 19:05
*/
public class CompanyDto extends BaseBean {
    private Long id;
    private String companyName;
    private String creditCode;
    private Integer companyType;
    private Integer companyNature;
    private Integer companyMark;
    private Integer companyStatus;
    private Date modifyTime;
    private Long modifyEmp;

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

    public Integer getCompanyNature() {
        return companyNature;
    }

    public void setCompanyNature(Integer companyNature) {
        this.companyNature = companyNature;
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
    public Date getModifyTime() {
        return modifyTime;
    }

    @Override
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public Long getModifyEmp() {
        return modifyEmp;
    }

    @Override
    public void setModifyEmp(Long modifyEmp) {
        this.modifyEmp = modifyEmp;
    }
}
