package com.ucar.combination.model;

import java.math.BigInteger;
import java.util.Date;

/**
 * description:账户
 *
 * @author junqiang.zhang@ucarinc.com
 * @version 1.0
 * @date: 2019/7/31
 **/
public class Account {
    //id
    private BigInteger id;
    //用户名
    private String name;
    //用户密码
    private String password;
    //员工id
    private BigInteger staffId;
    //员工名
    private String staffName;
    //数据权限类型
    private int premissions;
    //密保邮箱
    private String secretEmail;
    //创建时间
    private Date createTime;
    //创建人id
    private BigInteger creatEmpId;
    //创建人
    private String creatEmpName;
    //修改时间
    private Date modifyTime;
    //修改人id
    private BigInteger modifyEmpId;
    //修改人
    private String modifyEmpName;
    //账号状态
    private  int status;

    private String remark;
    //备注

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigInteger getStaffId() {
        return staffId;
    }

    public void setStaffId(BigInteger staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public int getPremissions() {
        return premissions;
    }

    public void setPremissions(int premissions) {
        this.premissions = premissions;
    }

    public String getSecretEmail() {
        return secretEmail;
    }

    public void setSecretEmail(String secretEmail) {
        this.secretEmail = secretEmail;
    }

    public BigInteger getCreatEmpId() {
        return creatEmpId;
    }

    public void setCreatEmpId(BigInteger creatEmpId) {
        this.creatEmpId = creatEmpId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatEmpName() {
        return creatEmpName;
    }

    public void setCreatEmpName(String creatEmpName) {
        this.creatEmpName = creatEmpName;
    }

    public String getModifyEmpName() {
        return modifyEmpName;
    }

    public void setModifyEmpName(String modifyEmpName) {
        this.modifyEmpName = modifyEmpName;
    }

    public BigInteger getModifyEmpId() {
        return modifyEmpId;
    }

    public void setModifyEmpId(BigInteger modifyEmpId) {
        this.modifyEmpId = modifyEmpId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
