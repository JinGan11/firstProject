package com.ucar.combination.model;

import java.util.Date;

/**
 * @program: combination
 * @description: 历史密码实体类
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-07-31 09:32
 */
public class HisPassword {
    private long id;
    private long accountId;
    private String historyPassword;
    private Date createTime;
    private long createEmp;
    private Date modifyTime;
    private long modifyEmp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getHistoryPassword() {
        return historyPassword;
    }

    public void setHistoryPassword(String historyPassword) {
        this.historyPassword = historyPassword;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getCreateEmp() {
        return createEmp;
    }

    public void setCreateEmp(long createEmp) {
        this.createEmp = createEmp;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public long getModifyEmp() {
        return modifyEmp;
    }

    public void setModifyEmp(long modifyEmp) {
        this.modifyEmp = modifyEmp;
    }
}
