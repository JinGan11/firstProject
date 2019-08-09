package com.ucar.combination.model.dto;

import com.ucar.combination.model.Department;

/**
 * description:用于修改页面显示
 *
 * @author 郑开添（kaitian.zheng@ucarinc.com）
 * @version 1.0
 * @date 2019/8/9 11:16
 **/
public class DepartmentEditDto extends Department {

    private String upperDepartmentName;
    private String cityName;
    private String createName;
    private String modifyName;

    public String getUpperDepartmentName() {
        return upperDepartmentName;
    }

    public void setUpperDepartmentName(String upperDepartmentName) {
        this.upperDepartmentName = upperDepartmentName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getModifyName() {
        return modifyName;
    }

    public void setModifyName(String modifyName) {
        this.modifyName = modifyName;
    }
}
