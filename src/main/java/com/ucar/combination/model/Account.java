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
    private Long id;
    //用户名
    private String accountName;
    //用户密码
    private String password;
    //是否关联员工
    private int isRelStaff;
    //员工id
    private Long staffId;
    //员工名
    private String staffName;
    //员工编号
    private String staffNum;
    //所属部门id
    private Long departmentId;
    //所属部门
    private String department;
    //数据权限类型
    private int premissions;
    //密保邮箱
    private String secretEmail;
    //员工邮箱
    private String staffEmail;
    //创建时间
    private Date createTime;
    //创建人id
    private Long creatEmpId;
    //创建人
    private String creatEmpName;
    //修改时间
    private Date modifyTime;
    //修改人id
    private Long modifyEmpId;
    //修改人
    private String modifyEmpName;
    //账号状态
    private  int accountState;
    //备注
    private String remark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
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

    public Long getCreatEmpId() {
        return creatEmpId;
    }

    public void setCreatEmpId(Long creatEmpId) {
        this.creatEmpId = creatEmpId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getaccountState() {
        return accountState;
    }

    public void setaccountState(int accountState) {
        this.accountState = accountState;
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

    public Long getModifyEmpId() {
        return modifyEmpId;
    }

    public void setModifyEmpId(Long modifyEmpId) {
        this.modifyEmpId = modifyEmpId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(String staffNum) {
        this.staffNum = staffNum;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public int getIsRelStaff() {
        return isRelStaff;
    }

    public void setIsRelStaff(int isRelStaff) {
        this.isRelStaff = isRelStaff;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }
}
