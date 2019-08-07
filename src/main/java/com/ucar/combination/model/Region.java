package com.ucar.combination.model;

import com.ucar.combination.model.dto.BaseBean;

import java.sql.Timestamp;

/**
 * description:
 *
 * @author 杨锴伦（kailun.yang@ucarinc.com）
 * @version 1.0
 * @date 2019-08-06 16:45
 **/
public class Region extends BaseBean {
    private Long cityID;
    private String regionCode;
    private String countryCode;
    private String regionAreaCode;
    private String regionPinyin;
    private String regionName;
    private Integer regionLevel;
    private Long upperRegionID;
    private String upperRegion;
    private Integer regionStatus;
    private String cEmp;
    private Timestamp cTime;
    private String mEmp;
    private Timestamp mTime;
    private String remark;

    public Long getCityID() {
        return cityID;
    }

    public void setCityID(Long cityID) {
        this.cityID = cityID;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getRegionAreaCode() {
        return regionAreaCode;
    }

    public void setRegionAreaCode(String regionAreaCode) {
        this.regionAreaCode = regionAreaCode;
    }


    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRegionPinyin() {
        return regionPinyin;
    }

    public void setRegionPinyin(String regionPinyin) {
        this.regionPinyin = regionPinyin;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getRegionLevel() {
        return regionLevel;
    }

    public void setRegionLevel(Integer regionLevel) {
        this.regionLevel = regionLevel;
    }

    public Long getUpperRegionID() {
        return upperRegionID;
    }

    public void setUpperRegionID(Long upperRegionID) {
        this.upperRegionID = upperRegionID;
    }

    public String getUpperRegion() {
        return upperRegion;
    }

    public void setUpperRegion(String upperRegion) {
        this.upperRegion = upperRegion;
    }

    public Integer getRegionStatus() {
        return regionStatus;
    }

    public void setRegionStatus(Integer regionStatus) {
        this.regionStatus = regionStatus;
    }

    public String getcEmp() {
        return cEmp;
    }

    public void setcEmp(String cEmp) {
        this.cEmp = cEmp;
    }

    public Timestamp getcTime() {
        return cTime;
    }

    public void setcTime(Timestamp cTime) {
        this.cTime = cTime;
    }

    public String getmEmp() {
        return mEmp;
    }

    public void setmEmp(String mEmp) {
        this.mEmp = mEmp;
    }

    public Timestamp getmTime() {
        return mTime;
    }

    public void setmTime(Timestamp mTime) {
        this.mTime = mTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
