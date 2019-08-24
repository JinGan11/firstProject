package com.ucar.combination.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.AccountHistoryDao;
import com.ucar.combination.dao.AccountManageDao;
import com.ucar.combination.dao.EmployeeManageDao;
import com.ucar.combination.dao.PowerDao;
import com.ucar.combination.model.*;
import com.ucar.combination.model.dto.AccountPowerDto;
import com.ucar.combination.model.dto.StaffAccountDTO;
import com.ucar.combination.service.AccountManagerService;
import com.ucar.combination.service.DepartmentService;
import com.ucar.combination.service.EmployeeManageService;
import org.apache.ibatis.annotations.Param;
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
    @Resource
    private EmployeeManageService employeeManageService;

    /**
     * description: 账户管理列表
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：
     * @PArams：queryParam 查询条件
     * @Return：ResultPage 查询结果
     */
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

    /**
     * description: 账户管理excel
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date： 2019/8/15 12:25
     * @PArams：queryParam 查询条件
     * @Return：List 查询结果
     */
    @Override
    public List<Account> getAccountList(QueryParam queryParam) {
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
        return accountManageDao.queryList(queryParam);
    }

    @Override
    public Account selectAccountById(String id) {
        return accountManageDao.selectAccountById(id);
    }

    /**
     * description: 通过ID进行查找
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/19 19:29
     * @params id 账户ID

     * @return
     */
    public Account selectById(Long id){
        //有可改进的地方，一次性读取所有数据
        Account account=accountManageDao.selectById(id);
        if (account!=null&&account.getCreatEmpId()!=null){
            Account account1=accountManageDao.selectById(account.getCreatEmpId());
           String createName=accountManageDao.getStaffNameByAccountId(account.getCreatEmpId());
            if (account1!=null&&account1.getAccountName()!=null){
                account.setCreatEmpName(account1.getAccountName());
            }
           if (createName!=null){
			   account.setCreatEmpName(account1.getAccountName()+"("+createName+")");
		   }
        }
        if (account!=null&&account.getModifyEmpId()!=null){
            Account account1=accountManageDao.selectById(account.getModifyEmpId());
            String modifyName=accountManageDao.getStaffNameByAccountId(account.getModifyEmpId());
            if (account1!=null&&account1.getAccountName()!=null){
                account.setModifyEmpName(account1.getAccountName());
            }
            if (modifyName!=null){
                account.setModifyEmpName(account1.getAccountName()+"("+modifyName+")");
            }
        }
        return account;
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
        accountStaff.setOperationType("新建账号");
        accountStaff.setAccountState(1);
        accountStaff.setCreater(accountManageDao.selectStaffIdById(accountStaff.getCreateEmp()));
        accountStaff.setModifier(accountManageDao.selectStaffIdById(accountStaff.getModifyEmp()));
        accountManageDao.insertAccount(accountStaff);
        accountStaff.setAccountId(accountManageDao.selectIdByNum(accountStaff.getAccountName()));
        insertAccountHistory(accountStaff);
    }

    /**
     * description: 查询角色账号明细
     * @uthor： zhengyong.liao@ucarinc.com
     * @Date：  2019/8/7
     * @PArams：
     * @Return：
     */
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
 /*   @Override
    public Account selectById(Long id) {
        return accountManageDao.selectById(id); accountManagerService.selectById
    }*/

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
        Long creater = accountManageDao.selectStaffIdById(accountStaff.getCreateEmp());
        accountStaff.setCreater(creater);
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
        accountStaff.setOperationType("修改账号");
        insertAccountHistory(accountStaff);
        accountStaff.setModifier(accountManageDao.selectStaffIdById(accountStaff.getModifyEmp()));
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
        accountStaff.setModifier(accountManageDao.selectStaffIdById(accountStaff.getModifyEmp()));
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
        accountStaff.setModifier(accountManageDao.selectStaffIdById(accountStaff.getModifyEmp()));
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
        account.setModifierId(accountManageDao.selectStaffIdById(account.getModifyEmpId()));
        account.setModifyTime(date);
        account.setId(Long.parseLong(String.valueOf(id)));
         //拼装一条历史记录
        AccountHistory accountHistory = accountManageDao.selectAccountInfoById(id);
        accountHistory.setCreateEmp(Long.parseLong(currentAccountId));
        accountHistory.setCreateTime(date);
        accountHistory.setAccountState(Byte.parseByte(String.valueOf(status)));
        accountHistory.setHistoryOperationType(historyOperationType);
        accountHistory.setCreater(selectStaffIdById(accountHistory.getCreateEmp()));

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

    @Override
    public List<AccountHistory> getHistoryList(String accountId) {
        return accountHistoryDao.selectAccountHistoryByAccountId(accountId);
    }

    @Override
    public void updateModifyTimeAndModifyName(@Param("a") Account account){
        accountManageDao.updateModifyTimeAndModifyName(account);
    }

    @Override
    public Long selectStaffIdById(Long accountId){
        return accountManageDao.selectStaffIdById(accountId);
    }

    @Override
    public Result updateAccountStatue(Long accountId, Long operateAccountId) {
        Long staffId = accountManageDao.selectStaffIdById(operateAccountId);
        Account account = accountManageDao.selectAccountById(String.valueOf(accountId));
        AccountStaff accountStaff = new AccountStaff();
        accountStaff.setStaffId(account.getStaffId());
        accountStaff.setPermissions(account.getPremissions());
        accountStaff.setSecretEmail(account.getSecretEmail());
        accountStaff.setModifier(staffId);
        accountStaff.setModifyEmp(operateAccountId);
        accountStaff.setRemark(account.getRemark());
        accountStaff.setAccountId(accountId);
        accountManageDao.modifyAccount(accountStaff);
        return Result.ok();
    }

    /**
     * description: 根据账户id查询账户状态
     * @author junqiang.zhang@ucarinc.com
     * @date: 2019/8/20
     * @params: accountId 账户id
     * @return: int 账户状态
     */
    @Override
    public int getAccountStateById(Long accountId){
        return accountManageDao.getAccountStateById(accountId);
    }

    /**
     * description: 根据员工id查询员工信息
     * @author junqiang.zhang@ucarinc.com
     * @date: 2019/8/20
     * @params: staffId 员工id
     * @return: flag 离职，是否删除，是否关联账户的标志
     */
    @Override
    public int getStaffInfBystaffId(Long staffId){
        int flag = 0;
        if(staffId != null && staffId != 0) {
            Staff staff = employeeManageDao.getStaffInfById(staffId);
            if(staff.getStatus() == 0)  {
                flag = 1;
            } else if (staff.getIsDimission() == 1) {
                flag = 2;
            } else if (staff.getAccountId() != null && staff.getAccountId() != 0) {
                flag = 3;
            }
        }
        return flag;
    }

    @Override
    public Account selectAccountByNum(String accountName) {
        Account account = accountManageDao.selectAccountByNum(accountName);
        return account;
    }
}
