package com.ucar.combination.dao;

import com.ucar.combination.model.LoginUser;

import java.util.List;

public interface UserDao {
    List<LoginUser> qryAccountPwdByAccountName(LoginUser loginUser);
}
