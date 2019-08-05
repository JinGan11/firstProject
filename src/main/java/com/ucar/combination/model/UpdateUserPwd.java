package com.ucar.combination.model;

/**
 * @version 1.0
 * @description: 修改密码vo
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-08-05 13:13
 */
public class UpdateUserPwd {
    private Long id;
    private String accountName;
    private String oldPassword;
    private String newPassword;

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

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
