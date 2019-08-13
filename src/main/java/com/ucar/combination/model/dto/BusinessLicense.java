package com.ucar.combination.model.dto;

public class BusinessLicense {
    private Long companyId;
    private String fileLocation;

    public BusinessLicense() {
    }

    public BusinessLicense(Long companyId, String fileLocation) {
        this.companyId = companyId;
        this.fileLocation = fileLocation;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
}
