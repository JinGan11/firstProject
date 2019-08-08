package com.ucar.combination.model.dto;

import java.util.List;

/**
 * description: DepartmentTreeDto的拓展，新增canChoose属性，用于修改上级部门的情况
 *
 * @author 郑开添（kaitian.zheng@ucarinc.com）
 * @version 1.0
 * @date 2019/8/7 15:17
 **/
public class DepartmentUpperDto {

    private Long id;
    private String departmentNo;
    private String departmentName;
    private String upperDepartmentNo;
    private Integer status;
    private String supportBusiness;
    private List<DepartmentUpperDto> children;
    private Boolean canChoose; // 修改上级部门时该节点是否可选

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<DepartmentUpperDto> getChildren() {
        return children;
    }

    public void setChildren(List<DepartmentUpperDto> children) {
        this.children = children;
    }

    public String getSupportBusiness() {
        return supportBusiness;
    }

    public void setSupportBusiness(String supportBusiness) {
        this.supportBusiness = supportBusiness;
    }

    public Boolean getCanChoose() {
        return canChoose;
    }

    public void setCanChoose(Boolean canChoose) {
        this.canChoose = canChoose;
    }
}
