package com.ucar.combination.service;

import com.ucar.combination.common.Result;
import com.ucar.combination.common.ReturnResult;
import com.ucar.combination.model.User;
import com.ucar.combination.model.UpdateUserPwd;

public interface UserService {

    Result login(User loginUser);
    ReturnResult updatePassword(UpdateUserPwd userPwdVo);
    Result getEmpInfo(User user);
}
