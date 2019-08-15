package com.ucar.combination.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.AccountHistoryDao;
import com.ucar.combination.dao.AccountManageDao;
import com.ucar.combination.dao.EmployeeManageDao;
import com.ucar.combination.dao.PowerDao;
import com.ucar.combination.model.*;
import com.ucar.combination.model.dto.AccountPowerDto;
import com.ucar.combination.service.AccountManagerService;
import com.ucar.combination.service.DepartmentService;
import com.ucar.combination.service.EmployeeManageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
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
    @Resource
    private PowerDao powerDao;
    @Resource
    private AccountHistoryDao accountHistoryDao;
    @Resource
    private DepartmentService departmentService;

    @Override
    public ResultPage queryList(QueryParam queryParam) {
        Long userId = (Long)queryParam.get("userId");
        List<Long> departmentIdList = departmentService.selectDataPowerIds(userId);
        int permission = accountManageDao.selectPermissionsById(userId);
        queryParam.put("userPermission",permission);
        Long [] departmentIds = new Long[departmentIdList.size()];
        if(permission != 1 && permission != 4) {
            int i = 0;
            for (Long id : departmentIdList) {
                departmentIds[i] = id;
                i++;
            }
            queryParam.put("departmentIds",departmentIds);
        }
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
     *
     * @return 无
     * @author shiya.li@ucarinc.com
     * @date 2019/8/6 17：32
     * @params id 账户id
     */
    public void updateStatus(int id, int status) {
        accountManageDao.updateStatus(id, status);
    }

    @Override
    public void insertAccount(AccountStaff accountStaff) {
        accountStaff.setPassword(DigestUtils.md5DigestAsHex((accountStaff.getPassword()).getBytes()));
        accountManageDao.insertAccount(accountStaff);
        accountStaff.setOperationType("新建账号");
        accountStaff.setAccountId(accountManageDao.selectIdByNum(accountStaff.getAccountName()));
        insertAccountHistory(accountStaff);
    }

    //lzy
    @Override
    public ResultPage getRoleAccountList(QueryParam queryParam) {
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<RoleAccount> list = accountManageDao.getRoleAccountList(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }

    @Override
    public List<RoleAccount> queryRoleAccountList(QueryParam queryParam) {
        return accountManageDao.getRoleAccountList(queryParam);
    }


    /**
     * description: 员工账号状态
     *
     * @return 状态
     * @author jing.luo01@ucarinc.com
     * @date 2019/8/8 9:57
     * @params ID员工ID
     */
    @Override
    public int updateState(String id) {
        return accountManageDao.updateState(id);
    }

    /**
     * description: 账户查找
     *
     * @return ACCOUNT
     * @author jing.luo01@ucarinc.com
     * @date 2019/8/8 10:34
     * @params 账户ID
     */
    @Override
    public Account selectById(Long id) {
        return accountManageDao.selectById(id);
    }

    /**
     * description:插入历史记录
     *
     * @return
     * @author jing.luo01@ucarinc.com
     * @date 2019/8/8 10:43
     * @params 一个MAP存储着所有信息
     */
    @Override
    public int insertAccountHistory(AccountStaff accountStaff) {
        return accountManageDao.insertAccountHistory(accountStaff);
    }

    /**
     * description: 修改账户信息
     * @author junqiang.zhang@ucarinc.com
     * @date:  2019/8/9 11:25
     * @params: accountStaff 要修改的信息
     * @return:
     */
    @Override
    public int modifyAccount(AccountStaff accountStaff) {
        return accountManageDao.modifyAccount(accountStaff);
    }

    /**
     * description: 修改员工的账户信息
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date： 2019/8/9 13:04
     * @params accountStaff 要修改的信息
     * @return
     */
    @Override
    public int updateStaffAccount(AccountStaff accountStaff) {
        accountStaff.setOperationType("修改账号");
        insertAccountHistory(accountStaff);
        return employeeManageDao.updateAccount(accountStaff);
    }

    /**
     * description: 账户权限明细查询
     *
     * @return
     * @author jing.luo01@ucarinc.com
     * @date 2019/8/9 15:10
     * @params queryParam 描述
     */
    @Override
    public ResultPage getAccountPowerList(QueryParam queryParam) {
        //return
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<AccountPowerDto> list = powerDao.getAccountPowerList(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());

    }

    /**
     * description: 手动选择数据权限新增
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：  2019/8/10 10:25
     * @PArams：accountId账户id tree部门权限
     * @Return：
     */
    @Override
    public void insertDepartmentPower(Long accountId, String tree) {
        DepartmentPower departmentPower = new DepartmentPower();
        departmentPower.setAccountId(accountId);
        departmentPower.setDepartmentId(tree.split(" "));
        accountManageDao.insertDepartmentPower(departmentPower);

    }

    /**
     * description: 手动选择数据权限删除权限
     * @author junqiang.zhang@ucarinc.com
     * @date:  2019/8/10 10:55
     * @params: accountId 账户id
     * @return:
     */
    @Override
    public int deleteDepartmentPower(Long accountId) {
        return accountManageDao.deleteDepartmentPower(accountId);
    }

    /**
     * description: 查询账户的部门权限
     * @author junqiang.zhang@ucarinc.com
     * @date:  2019/8/10 12:30
     * @params: accountId 账户id
     * @return: List 账户拥有的部门权限的部门id
     */
    @Override
    public List<String> selectDeparentPowerByAccountId(Long accountId) {
        return accountManageDao.selectDeparentPowerByAccountId(accountId);
    }

    /**
     * description: 删除账户（账户状态改为无效）
     * @author junqiang.zhang@ucarinc.com
     * @date:  2019/8/10 14:30
     * @params: accountStaff 账户id和修改人id
     * @return:
     */
    @Override
    public int deleteAccountById(AccountStaff accountStaff) {
        accountStaff.setOperationType("删除账号");
        insertAccountHistory(accountStaff);
        return accountManageDao.deleteAccountById(accountStaff);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void lockAndUnlock(int id, int status, String historyOperationType, HttpServletRequest request) {
        //拿到当前登录账号的名字
        HttpSession session = request.getSession();
        String currentAccountId = session.getAttribute("accountId").toString();
        Date date = new Date();
        Account account = new Account();
        account.setModifyEmpId(Long.parseLong(currentAccountId));
        account.setModifyTime(date);
        account.setId(Long.parseLong(String.valueOf(id)));
         //拼装一条历史记录
        AccountHistory accountHistory = accountManageDao.selectAccountInfoById(id);
        accountHistory.setAccountState(Byte.parseByte(String.valueOf(status)));
        accountHistory.setHistoryOperationType(historyOperationType);

        accountManageDao.updateModifyTimeAndModifyName(account);
        accountManageDao.updateStatus(id, status);
        accountHistoryDao.insert(accountHistory);
    }
    /**
     * description: 查询账户名是否存在
     * @author junqiang.zhang@ucarinc.com
     * @date:  2019/8/13 10:41
     * @params: accountName 账户名
     * @return: int 查询到的账户条数
     */
    @Override
    public int selectAccountByAccountName(String accountName) {
        List account = accountManageDao.selectAccountByAccountName(accountName);
        return account.size();
    }
    /**
     * description: 员工更改邮箱地址，关联的有效账号的邮箱同步更新
     * @author shiya.li@ucarinc.com
     * @date:  2019/8/14 22:40
     * @params: accountName 账户名
     * @return: List<String> 查询到的账户
     */
    public void updateAccountSecretEmailById(String secretEmail,String id){
        accountManageDao.updateAccountSecretEmailById(secretEmail,id);
    }
    /**
     * description: 得到所有的AccountPowerDto信息，用于导出所有数据时用
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/15 9:49
     * @params queryParam 描述

     * @return List<AccountPowerDto>返回AccountPowerDto的集合
     */
    @Override
    public List<AccountPowerDto> getAllAccountPowerList(QueryParam queryParam) {
        return powerDao.getAccountPowerList(queryParam);
    }
}
