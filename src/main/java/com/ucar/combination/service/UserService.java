package com.ucar.combination.service;

import com.ucar.combination.common.Result;
import com.ucar.combination.common.ReturnResult;
import com.ucar.combination.model.LoginUser;
import com.ucar.combination.model.User;

public interface UserService {

    ReturnResult login(LoginUser loginUser);
    ReturnResult updatePassword(User user);
    ReturnResult getEmpInfo(User user);
}
