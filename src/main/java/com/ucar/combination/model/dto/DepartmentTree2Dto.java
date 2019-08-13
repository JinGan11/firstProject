package com.ucar.combination.model.dto;

import java.util.List;

/**
 * description:建立部门树的dto
 *
 * @author 郑开添（kaitian.zheng@ucarinc.com）
 * @version 2.0
 * @date 2019/8/12 17:04
 **/
public class DepartmentTree2Dto {

    private Long id;
    private String departmentNo;
    private String departmentName;
    private String upperDepartmentNo;
    private String supportBusiness;
    private Integer status;

    private List<DepartmentTree2Dto> children;

    private Boolean canChoose;
    private Boolean nodeIsLeaf;

    public List<DepartmentTree2Dto> getChildren() {
        return children;
    }

    public void setChildren(List<DepartmentTree2Dto> children) {
        this.children = children;
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

    public String getSupportBusiness() {
        return supportBusiness;
    }

    public void setSupportBusiness(String supportBusiness) {
        this.supportBusiness = supportBusiness;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getCanChoose() {
        return canChoose;
    }

    public void setCanChoose(Boolean canChoose) {
        this.canChoose = canChoose;
    }

    public Boolean getNodeIsLeaf() {
        return nodeIsLeaf;
    }

    public void setNodeIsLeaf(Boolean nodeIsLeaf) {
        this.nodeIsLeaf = nodeIsLeaf;
    }

}
