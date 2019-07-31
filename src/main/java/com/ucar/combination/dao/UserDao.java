package com.ucar.combination.dao;

import com.ucar.combination.model.HisPassword;
import com.ucar.combination.model.LoginUser;
import com.ucar.combination.model.User;

import java.util.List;

public interface UserDao {

    /**
     * description: 通过用户名查询用户信息
     * @author peng.zhang11@ucarinc.com
     * @date   2019/7/30 17:09
     * @params user 用户信息
     * @return List结果集
     */
    List<User> qryAccountByAccountName(User user);

    /**
     * description: 通过用户名查询密码
     * @author peng.zhang11@ucarinc.com
     * @date   2019/7/30 17:01
     * @params loginUser 用户登陆信息
     * @return 返回用户列表
     */
    List<LoginUser> qryAccountPwdByAccountName(LoginUser loginUser);

    /**
     * description: 查询历史密码
     * @author peng.zhang11@ucarinc.com
     * @date   2019/7/30 17:02
     * @params
     * @return
     */
    List<HisPassword> qryHistoryPwdById(User user);

    /**
     * description: 修改密码
     * @author peng.zhang11@ucarinc.com
     * @date   2019/7/30 17:51
     * @params user 用户信息
     * @return 无
     */
    void updatePwdById(User user);

    /**
     * description: 将旧密码插入到历史密码表中
     * @author peng.zhang11@ucarinc.com
     * @date   2019/7/30 18:02
     * @params user 用户信息
     * @return 无
     */
    void insertHisPwd(User user);

}
