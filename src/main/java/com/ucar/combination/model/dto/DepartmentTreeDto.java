package com.ucar.combination.model.dto;

import java.util.List;

/**
 * description:用于构建部门树结构
 *
 * @author 郑开添（kaitian.zheng@ucarinc.com）
 * @version 1.1
 * @date 2019/8/5 13:41
 **/
public class DepartmentTreeDto {

    private Long id;
    private String departmentNo;
    private String departmentName;
    private String upperDepartmentNo;
    private Integer status;
    private List<DepartmentTreeDto> children;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getUpperDepartmentNo() {
        return upperDepartmentNo;
    }

    public void setUpperDepartmentNo(String upperDepartmentNo) {
        this.upperDepartmentNo = upperDepartmentNo;
    }

    public List<DepartmentTreeDto> getChildren() {
        return children;
    }

    public void setChildren(List<DepartmentTreeDto> children) {
        this.children = children;
    }

}
