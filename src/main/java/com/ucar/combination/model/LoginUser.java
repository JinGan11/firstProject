package com.ucar.combination.model;

/**
 * @program: combination
 * @description: 用户类
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-07-29 12:39
 */
public class LoginUser {
    private String accountName;
    private String accountPassword;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }
}
