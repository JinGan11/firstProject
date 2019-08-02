package com.ucar.combination.model;

/**
 * Description: 角色实体类
 *
 * @author gan.jin@ucarinc.com
 * @version 1.0
 * @Date 2019/8/2 14:44
 */
public class Role {

    private long roleid;
    private String rolename;
    private String accountid;
    private long staffid;
    private String accountname;
    private String departmentid;
    private Integer rolestatus;
    private String description;

    public void setRoleid(long roleid) {
        this.roleid = roleid;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public void setStaffid(long staffid) {
        this.staffid = staffid;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid;
    }

    public void setRolestatus(Integer rolestatus) {
        this.rolestatus = rolestatus;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getRoleid() {
        return roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public String getAccountid() {
        return accountid;
    }

    public long getStaffid() {
        return staffid;
    }

    public String getAccountname() {
        return accountname;
    }

    public String getDepartmentid() {
        return departmentid;
    }

    public Integer getRolestatus() {
        return rolestatus;
    }

    public String getDescription() {
        return description;
    }
}
