package com.ucar.combination.model;

import com.ucar.combination.model.dto.BaseBean;

/**
 * @version 1.0
 * @description: 账户特殊权限实体类
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-08-06 15:58
 */
public class AccountPower extends BaseBean {
    private Long id;
    private Long accountId;
    private Long powerId;

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

    public Long getPowerId() {
        return powerId;
    }

    public void setPowerId(Long powerId) {
        this.powerId = powerId;
    }
}
