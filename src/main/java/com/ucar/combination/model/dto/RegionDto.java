package com.ucar.combination.model.dto;

/**
 * description:
 *
 * @author 杨锴伦（kailun.yang@ucarinc.com）
 * @version 1.0
 * @date 2019-08-14 18:54
 **/
public class RegionDto {
    private Long cityID;
    private String regionCode;
    private String regionName;
    private boolean regionLeaf=false;
    private Integer regionStatus;
    private Integer regionLevel;

    public Integer getRegionStatus() {
        return regionStatus;
    }

    public void setRegionStatus(Integer regionStatus) {
        this.regionStatus = regionStatus;
    }

    public Integer getRegionLevel() {
        return regionLevel;
    }

    public void setRegionLevel(Integer regionLevel) {
        this.regionLevel = regionLevel;
    }

    public boolean getRegionLeaf() {
        return regionLeaf;
    }

    public void setRegionLeaf(boolean regionLeaf) {
        this.regionLeaf = regionLeaf;
    }

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

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

}
