package com.ucar.combination.service.impl;

import com.ucar.combination.common.Result;
import com.ucar.combination.dao.UserDao;
import com.ucar.combination.model.LoginUser;
import com.ucar.combination.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 * @program: combination
 * @description: 用户实现类
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-07-29 11:40
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Result login(LoginUser loginUser) {
        List<LoginUser> list = userDao.qryAccountPwdByAccountName(loginUser);
        if (list.size() == 0) {
            return Result.error(300,"没有该用户");
        } else {
            String md5Password = DigestUtils.md5DigestAsHex(loginUser.getAccountPassword().getBytes());
            if (md5Password.equals(list.get(0).getAccountPassword())) {
                return Result.ok("成功");
            } else {
                return Result.error(300,"密码错误");
            }
        }
    }
}
