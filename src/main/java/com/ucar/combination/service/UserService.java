package com.ucar.combination.service;

import com.ucar.combination.common.Result;
import com.ucar.combination.model.LoginUser;

public interface UserService {
    Result login(LoginUser loginUser);
}
