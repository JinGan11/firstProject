package com.ucar.combination.model;

import java.util.Date;

/**
 * @version 1.0
 * @description: 重置密码
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-08-29 17:07
 */
public class ResetPass {
    private Long id;
    private Long accountId;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
