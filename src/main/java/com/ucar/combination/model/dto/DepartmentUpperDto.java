package com.ucar.combination.model.dto;

/**
 * description: DepartmentTreeDto的拓展，新增canChoose属性，用于修改上级部门的情况
 *
 * @author 郑开添（kaitian.zheng@ucarinc.com）
 * @version 1.0
 * @date 2019/8/7 15:17
 **/
public class DepartmentUpperDto extends DepartmentTreeDto{

    // 修改上级部门时该节点是否可选
    private Boolean canChoose;

    private String supportBusiness;

    public Boolean getCanChoose() {
        return canChoose;
    }

    public void setCanChoose(Boolean canChoose) {
        this.canChoose = canChoose;
    }

    public String getSupportBusiness() {
        return supportBusiness;
    }

    public void setSupportBusiness(String supportBusiness) {
        this.supportBusiness = supportBusiness;
    }
}
