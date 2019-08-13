package com.ucar.combination.model;

/**
 * description:
 *
 * @Author junqiang.zhang@ucarinc.com
 * @Version 1.0
 * @Date: 2019/8/10 9:07
 **/
public class DepartmentPower {
    Long id;
    String[] departmentId;
    Long accountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String[] getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String[] departmentId) {
        this.departmentId = departmentId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
