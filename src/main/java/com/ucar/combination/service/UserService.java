package com.ucar.combination.service;

import com.ucar.combination.common.ReturnResult;
import com.ucar.combination.model.User;
import com.ucar.combination.model.UpdateUserPwd;

public interface UserService {

    ReturnResult login(User loginUser);
    ReturnResult updatePassword(UpdateUserPwd userPwdVo);
    ReturnResult getEmpInfo(User user);
}
