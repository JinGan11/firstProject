package com.ucar.combination.model.dto;

import java.util.Date;

/**
 * Description: 用于连接角色账户员工
 *
 * @author gan.jin@ucarinc.com
 * @version 1.0
 * @Date 2019/8/13 10:16
 */
public class RoleAccountStaffDto {
    private String createEmp;
    private String createEmpNum;
    private String createEmpName;
    private Date createTime;
    private String modifyEmp;
    private String modifyEmpNum;
    private String modifyEmpName;
    private Date modifyTime;


    public String getCreateEmp() {
        return createEmp;
    }

    public void setCreateEmp(String createEmp) {
        this.createEmp = createEmp;
    }

    public String getModifyEmp() {
        return modifyEmp;
    }

    public void setModifyEmp(String modifyEmp) {
        this.modifyEmp = modifyEmp;
    }

    public String getCreateEmpNum() {
        return createEmpNum;
    }

    public void setCreateEmpNum(String createEmpNum) {
        this.createEmpNum = createEmpNum;
    }

    public String getCreateEmpName() {
        return createEmpName;
    }

    public void setCreateEmpName(String createEmpName) {
        this.createEmpName = createEmpName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifyEmpNum() {
        return modifyEmpNum;
    }

    public void setModifyEmpNum(String modifyEmpNum) {
        this.modifyEmpNum = modifyEmpNum;
    }

    public String getModifyEmpName() {
        return modifyEmpName;
    }

    public void setModifyEmpName(String modifyEmpName) {
        this.modifyEmpName = modifyEmpName;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
