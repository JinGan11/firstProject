package com.ucar.combination.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.AccountManageDao;
import com.ucar.combination.dao.EmployeeManageDao;
import com.ucar.combination.model.Account;
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
    /**
     * description:由账户id将账户状态设置为无效
     * @author shiya.li@ucarinc.com
     * @date   2019/8/6 17：32
     * @params id 账户id
     * @return 无
     */
    public void updateStatus(int id){
        accountManageDao.updateStatus(id);

    }

    @Override
    public Long insertAccount(Map<String, Object> map) {
        map.replace("password",DigestUtils.md5DigestAsHex(((String)map.get("password")).getBytes()));
        accountManageDao.insertAccount(map);
        return accountManageDao.selectIdByNum((String)map.get("accountNum"));
    }

    @Override
    public int updateStaff(Map<String, Object> map) {
        return employeeManageDao.updateStaffAccount(map);
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
}
