package com.ucar.combination.model;

import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * description:部门类
 *
 * @author 郑开添（kaitian.zheng@ucarinc.com）
 * @version 1.0
 * @date 2019/7/30 11:11
 **/
public class Department {

    private BigInteger id;
    private String department_no;
    private Integer workplace;
    private String department_name;
    private BigInteger staff_id;
    private String staff_name;
    private Integer city_id;
    private String address;
    private String telephone;
    private String email;
    private String landline;
    private Integer level;
    private String upper_department_no;
    private String support_business;
    private Integer department_type;
    private Integer status;
    private BigInteger create_emp;
    private Timestamp create_time;
    private BigInteger modify_emp;
    private Timestamp modify_time;
    private String remark;

    public Department(BigInteger id, String department_no, String department_name, String upper_department_no) {
        this.id = id;
        this.department_no = department_no;
        this.department_name = department_name;
        this.upper_department_no = upper_department_no;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getDepartment_no() {
        return department_no;
    }

    public void setDepartment_no(String department_no) {
        this.department_no = department_no;
    }

    public Integer getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Integer workplace) {
        this.workplace = workplace;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public BigInteger getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(BigInteger staff_id) {
        this.staff_id = staff_id;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getUpper_department_no() {
        return upper_department_no;
    }

    public void setUpper_department_no(String upper_department_no) {
        this.upper_department_no = upper_department_no;
    }

    public String getSupport_business() {
        return support_business;
    }

    public void setSupport_business(String support_business) {
        this.support_business = support_business;
    }

    public Integer getDepartment_type() {
        return department_type;
    }

    public void setDepartment_type(Integer department_type) {
        this.department_type = department_type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigInteger getCreate_emp() {
        return create_emp;
    }

    public void setCreate_emp(BigInteger create_emp) {
        this.create_emp = create_emp;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public BigInteger getModify_emp() {
        return modify_emp;
    }

    public void setModify_emp(BigInteger modify_emp) {
        this.modify_emp = modify_emp;
    }

    public Timestamp getModify_time() {
        return modify_time;
    }

    public void setModify_time(Timestamp modify_time) {
        this.modify_time = modify_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
