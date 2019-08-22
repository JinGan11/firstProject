package com.ucar.combination.service.impl;

import com.ucar.combination.common.Result;
import com.ucar.combination.common.ReturnResult;
import com.ucar.combination.dao.EmployeeManageDao;
import com.ucar.combination.dao.UserDao;
import com.ucar.combination.model.*;
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
     *
     * @return 登陆结果
     * @author peng.zhang11@ucarinc.com
     * @date 2019/7/31 16:48
     * @params loginUser 用户登陆信息
     */
    @Override
    public Result login(User loginUser) {
        List<User> list = userDao.qryAccountByAccountName(loginUser);
        if (list.size() == 0) {
            return Result.ok().put("code", 304)
                    .put("Msg", "没有该用户");
        } else {
            if (list.get(0).getAccountState() == 3) {
                return Result.ok().put("code", 303)
                        .put("Msg", "登录失败，账号失效");
            }
            String md5Password = DigestUtils.md5DigestAsHex(loginUser.getAccountPassword().getBytes());
            if (md5Password.equals(list.get(0).getAccountPassword())) {
                return Result.ok().put("code", 200)
                        .put("Msg", "登陆成功")
                        .put("list", list);
            } else {
                return Result.ok().put("code", 300)
                        .put("Msg", "密码错误");
            }
        }
    }

    /**
     * description: 修改密码
     *
     * @return 结果集
     * @author peng.zhang11@ucarinc.com
     * @date 2019/7/30 16:57
     * @params user 用户信息
     */
    @Override
    public ReturnResult updatePassword(UpdateUserPwd userPwd) {
        /**
         * 1.判断之前是否用过
         * 2.是-返回提示 重新设置密码
         * 3.否-插入数据库 并将旧密码移入密码历史表，返回给用户修改成功
         * 待完善：修改时间 修改人等字段的值的设置
         */
        ReturnResult result = new ReturnResult();
        result.setCode(200);
        result.setMsg("修改密码成功！");
        User oldUser = new User();
        oldUser.setAccountName(userPwd.getAccountName());
        oldUser.setId(userPwd.getId());
        oldUser.setAccountPassword(userPwd.getNewPassword());
        List<User> list;
        List<HisPassword> list2;
        User userInfo = null;
        String md5OldPassword = DigestUtils.md5DigestAsHex(userPwd.getOldPassword().getBytes());
        String md5NewPassword = DigestUtils.md5DigestAsHex(userPwd.getNewPassword().getBytes());
        userPwd.setNewPassword(md5NewPassword);
        try {
            // 获取完整的账号信息
            list = userDao.qryAccountByAccountName(oldUser);
            if (list.size() != 0) {
                userInfo = list.get(0);
                userPwd.setStaffId(userInfo.getStaffId());
            }
            if (!md5OldPassword.equals(userInfo.getAccountPassword())) {
                result.setMsg("原密码输入密码错误！");
                result.setCode(303);
                return result;
            }
            if (!md5NewPassword.equals(userInfo.getAccountPassword())) {
                // 查询历史密码
                list2 = userDao.qryHistoryPwdById(list.get(0));
                if (list2.size() == 0) {
                    //插入旧密码表
                    userDao.insertHisPwd(userInfo);
                    //更新新密码
                    userDao.updatePwdById(userPwd);
                    return result;
                } else {
                    if (list2.size() == 1 && !md5NewPassword.equals(list2.get(0).getHistoryPassword())) {
                        //插入旧密码表
                        userDao.insertHisPwd(userInfo);
                        //更新新密码
                        userDao.updatePwdById(userPwd);
                        return result;
                    }
                    if (list2.size() > 1 && !md5NewPassword.equals(list2.get(1).getHistoryPassword())
                            && !md5NewPassword.equals(list2.get(0).getHistoryPassword())) {
                        //插入旧密码表
                        userDao.insertHisPwd(userInfo);
                        //更新新密码
                        userDao.updatePwdById(userPwd);
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
     * description: 重置密码
     *
     * @return 结果集
     * @author peng.zhang11@ucarinc.com
     * @date 2019/7/30 16:57
     * @params user 用户信息
     */
    @Override
    public ReturnResult resetPassword(UpdateUserPwd userPwd) {
        /**
         * 1.判断之前是否用过
         * 2.是-返回提示 重新设置密码
         * 3.否-插入数据库 并将旧密码移入密码历史表，返回给用户修改成功
         * 待完善：修改时间 修改人等字段的值的设置
         */
        ReturnResult result = new ReturnResult();
        result.setCode(200);
        result.setMsg("修改密码成功！");
        User oldUser = new User();
        oldUser.setAccountName(userPwd.getAccountName());
        oldUser.setAccountPassword(userPwd.getNewPassword());
        List<User> list;
        List<HisPassword> list2;
        User userInfo = null;
        String md5NewPassword = DigestUtils.md5DigestAsHex(userPwd.getNewPassword().getBytes());
        userPwd.setNewPassword(md5NewPassword);
        try {
            // 获取完整的账号信息
            list = userDao.qryAccountByAccountName(oldUser);
            if (list.size() != 0) {
                userInfo = list.get(0);
                userPwd.setId(userInfo.getId());
            }
            if (!md5NewPassword.equals(userInfo.getAccountPassword())) {
                // 查询历史密码
                list2 = userDao.qryHistoryPwdById(list.get(0));
                if (list2.size() == 0) {
                    //插入旧密码表
                    userDao.insertHisPwd(userInfo);
                    //更新新密码
                    userDao.updatePwdById(userPwd);
                    return result;
                } else {
                    if (list2.size() == 1 && !md5NewPassword.equals(list2.get(0).getHistoryPassword())) {
                        //插入旧密码表
                        userDao.insertHisPwd(userInfo);
                        //更新新密码
                        userDao.updatePwdById(userPwd);
                        return result;
                    }
                    if (list2.size() > 1 && !md5NewPassword.equals(list2.get(1).getHistoryPassword())
                            && !md5NewPassword.equals(list2.get(0).getHistoryPassword())) {
                        //插入旧密码表
                        userDao.insertHisPwd(userInfo);
                        //更新新密码
                        userDao.updatePwdById(userPwd);
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
     *
     * @return 员工信息结果集
     * @author peng.zhang11@ucarinc.com
     * @date 2019/7/31 16:11
     * @params user 用户的账户
     */
    @Override
    public Result getEmpInfo(User user) {
        Result result = new Result();
        List<User> list = userDao.qryAccountByAccountName(user);
        List<Staff> staffList = new ArrayList<>();
        if (list.size() != 0) {
            staffList = employeeManageDao.qryStaffById(list.get(0));
            String sex = null;
            String isDimission = null;
            if (staffList.get(0).getStaffSex() == 1) {
                sex = "男";
            } else if (staffList.get(0).getStaffSex() == 2) {
                sex = "女";
            }
            if (staffList.get(0).getIsDimission() == 0) {
                isDimission = "在职";
            } else {
                isDimission = "离职";
            }
            result.put("list", staffList)
                    .put("sex", sex)
                    .put("isDimission", isDimission);
        }
        return result;
    }

    @Override
    public Result isFirstLogin(User user) {
        List<HisPassword> list = userDao.qryHistoryPwdById(user);
        if (list.size() == 0 || list == null) {
            return Result.ok().put("msg", "首次登陆系统，请先修改密码")
                    .put("code", 506);
        }
        return Result.ok();
    }

    /**
     * description: 获取所有账号密码快到期（提前7天）信息
     *
     * @return
     * @author peng.zhang11@ucarinc.com
     * @date 2019/8/12 11:22
     * @params
     */
    @Override
    public Result getAllAccountListByModifyTime() {
        List<Account> accountList = userDao.getAllAccountListByModifyTime();
        return Result.ok().put("accountList", accountList);
    }

    /**
     * description:
     *
     * @return
     * @author peng.zhang11@ucarinc.com
     * @date 2019/8/19 20:42
     * @params
     */
    @Override
    public Result isLogin(Long accountId) {
        User user = new User();
        user.setId(accountId);
        List<User> list = userDao.qryAccountByAccountName(user);
        return Result.ok().put("list", list);
    }
}
