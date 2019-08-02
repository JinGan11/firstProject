package com.ucar.combination.service.impl;

import com.ucar.combination.common.ReturnResult;
import com.ucar.combination.dao.EmployeeManageDao;
import com.ucar.combination.dao.UserDao;
import com.ucar.combination.model.HisPassword;
import com.ucar.combination.model.LoginUser;
import com.ucar.combination.model.Staff;
import com.ucar.combination.model.User;
import com.ucar.combination.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
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

    @Autowired
    private EmployeeManageDao employeeManageDao;

    /**
     * description: 登陆
     * @author peng.zhang11@ucarinc.com
     * @date   2019/7/31 16:48
     * @params loginUser 用户登陆信息
     * @return 登陆结果
     */
    @Override
    public ReturnResult login(LoginUser loginUser) {
        ReturnResult result = new ReturnResult();
        List<LoginUser> list = userDao.qryAccountPwdByAccountName(loginUser);
        if (list.size() == 0) {
            result.setCode(300);
            result.setMsg("没有该用户");
            return result;
        } else {
            String md5Password = DigestUtils.md5DigestAsHex(loginUser.getAccountPassword().getBytes());
            if (md5Password.equals(list.get(0).getAccountPassword())) {
                result.setCode(200);
                result.setMsg("登陆成功！");
                return result;
            } else {
                result.setCode(300);
                result.setMsg("密码错误");
                return result;
            }
        }
    }

    /**
     * description: 修改密码
     * @author peng.zhang11@ucarinc.com
     * @date 2019/7/30 16:57
     * @params user 用户信息
     * @return 结果集
     */
    @Override
    public ReturnResult updatePassword(User user) {
        /**
         * 1.判断之前是否用过
         * 2.是-返回提示 重新设置密码
         * 3.否-插入数据库 并将旧密码移入密码历史表，返回给用户修改成功
         * 待完善：修改时间 修改人等字段的值的设置
         */
        ReturnResult result = new ReturnResult();
        result.setCode(200);
        result.setMsg("修改密码成功！");
        List<User> list;
        List<HisPassword> list2;
        User userInfo = null;
        String md5Password = DigestUtils.md5DigestAsHex(user.getAccountPassword().getBytes());
        user.setAccountPassword(md5Password);
        try {
            // 获取完整的账号信息
            list = userDao.qryAccountByAccountName(user);
            if (list.size() != 0){
                userInfo = list.get(0);
                user.setId(userInfo.getId());
            }
            if (!md5Password.equals(userInfo.getAccountPassword())) {
                // 查询历史密码
                list2 = userDao.qryHistoryPwdById(list.get(0));
                if (list2.size() == 0){
                    //插入旧密码表
                    userDao.insertHisPwd(userInfo);
                    //更新新密码
                    userDao.updatePwdById(user);
                    return result;
                } else {
                    if (list2.size() == 1 && !md5Password.equals(list2.get(0).getHistoryPassword())){
                        //插入旧密码表
                        userDao.insertHisPwd(userInfo);
                        //更新新密码
                        userDao.updatePwdById(user);
                        return result;
                    }
                    if (list2.size() > 1 && !md5Password.equals(list2.get(1).getHistoryPassword())
                            && !md5Password.equals(list2.get(0).getHistoryPassword())){
                        //插入旧密码表
                        userDao.insertHisPwd(userInfo);
                        //更新新密码
                        userDao.updatePwdById(user);
                        return result;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setCode(300);
        result.setMsg("密码不能与近三次的密码相同");
        return result;
    }

    /**
     * description: 获取员工信息
     * @author peng.zhang11@ucarinc.com
     * @date   2019/7/31 16:11
     * @params user 用户的账户
     * @return 员工信息结果集
     */
    @Override
    public ReturnResult getEmpInfo(User user) {
        ReturnResult result = new ReturnResult();
        result.setCode(300);
        List<User> list = userDao.qryAccountByAccountName(user);
        List<Staff> staffList = new ArrayList<>();
        if (list.size() != 0) {
            staffList = employeeManageDao.qryStaffById(list.get(0));
            result.setList(staffList);
            result.setCode(200);
        }
        return result;
    }
}
