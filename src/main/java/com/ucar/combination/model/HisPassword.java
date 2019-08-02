package com.ucar.combination.model;

import com.ucar.combination.model.dto.BaseBean;

import java.util.Date;

/**
 * @program: combination
 * @description: 历史密码实体类
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-07-31 09:32
 */
public class HisPassword extends BaseBean {
    private Long id;
    private Long accountId;
    private String historyPassword;

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

    public String getHistoryPassword() {
        return historyPassword;
    }

    public void setHistoryPassword(String historyPassword) {
        this.historyPassword = historyPassword;
    }
}
