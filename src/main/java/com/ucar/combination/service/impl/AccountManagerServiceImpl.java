package com.ucar.combination.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.AccountManageDao;
import com.ucar.combination.dao.EmployeeManageDao;
import com.ucar.combination.model.Account;
import com.ucar.combination.model.AccountStaff;
import com.ucar.combination.model.RoleAccount;
import com.ucar.combination.service.AccountManagerService;
import com.ucar.combination.service.EmployeeManageService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * description:账户管理
 *
 * @Author junqiang.zhang@ucarinc.com
 * @Version 1.0
 * @Date: 2019/8/1 10:34
 **/
@Service
public class AccountManagerServiceImpl implements AccountManagerService {
    @Resource
    private AccountManageDao accountManageDao;
    @Resource
    private EmployeeManageDao employeeManageDao;

    @Override
    public ResultPage queryList(QueryParam queryParam) {
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<Account> list = accountManageDao.queryList(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }

    @Override
    public Account selectAccountById(String id) {
        return accountManageDao.selectAccountById(id);
    }

    /**
     * description:由账户id将账户状态设置为无效
     * @author shiya.li@ucarinc.com
     * @date   2019/8/6 17：32
     * @params id 账户id
     * @return 无
     */
    public void updateStatus(int id,int status){
        accountManageDao.updateStatus(id,status);
    }

    @Override
    public Long insertAccount(AccountStaff accountStaff) {
        accountStaff.setPassword(DigestUtils.md5DigestAsHex((accountStaff.getPassword()).getBytes()));
        accountManageDao.insertAccount(accountStaff);
        return accountManageDao.selectIdByNum(accountStaff.getAccountName());
    }

    //lzy
    @Override
    public ResultPage getRoleAccountList(QueryParam queryParam) {
        Page<?> page = PageHelper.startPage(1,10);
        List<RoleAccount> list = accountManageDao.getRoleAccountList(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }

    /**
     * description: 员工账号状态
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/8 9:57
     * @params ID员工ID
     * @return 状态
     */
    @Override
    public int updateState(String id) {
        return accountManageDao.updateState(id);
    }

    /**
     * description: 账户查找
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/8 10:34
     * @params 账户ID
     * @return ACCOUNT
     */
    @Override
    public Account selectById(Long id) {
        return accountManageDao.selectById(id);
    }
    /**
     * description:插入历史记录
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/8 10:43
     * @params 一个MAP存储着所有信息
     * @return
     */
    @Override
    public int insertAccountHistory(Map<String, Object> map) {
        return accountManageDao.insertAccountHistory(map);
    }

    /*
     * description: 修改账户信息
     * @author peng.zhang11@ucarinc.com
     * @date:  2019/8/9 11:25
     * @params: accountStaff 要修改的信息
     * @return:
     */
    @Override
    public int modifyAccount(AccountStaff accountStaff) {
        return accountManageDao.modifyAccount(accountStaff);
    }

    /*
     * description: 修改员工的账户信息
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date： 2019/8/9 13:04
     * @params accountStaff 要修改的信息
     * @return
     */
    @Override
    public int updateStaffAccount(AccountStaff accountStaff) {
        return employeeManageDao.updateAccount(accountStaff);
    }
}
