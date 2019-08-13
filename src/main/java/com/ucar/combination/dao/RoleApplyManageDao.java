package com.ucar.combination.dao;
import com.ucar.combination.model.dto.*;

import java.util.List;
import java.util.Map;

public interface RoleApplyManageDao {


    /**
    * @Description:  角色申请管理列表
    * @Author
    * @Params
    * @Return
    * @Date  9:34 2019/8/3
    */
    List<RoleApplyDto> queryList(Map<String, Object> map);

    /**
    * @Description:  删除角色申请
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  15:22 2019/8/7
    */
    void deleteRoleApply(int id);

    /**
    * @Description:  提交审核
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  17:39 2019/8/7
    */
    void commitRoleApply(int id);
    
    /**
    * @Description:  角色申请 新建
    * @Author: min.zhang08@ucarinc.com  
    * @Params  
    * @Return  
    * @Date  15:00 2019/8/8
    */

    void createRoleApply(CreateRoleApplyDto createRoleApplyDto);


    /**
    * @Description:  角色申请 修改页面账户列表
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  10:56 2019/8/9
    */
    List<AccountListByApplyIdDto> showAccountListByApplyId(Long applyId);

    /**
    * @Description: 角色申请  获取当前登录账号的 具体信息
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  20:30 2019/8/9
    */

    LoginInfoInRoleApplyDto queryLoginInRoleApply(String applyAccountName);

    /**
    * @Description:  角色申请 新建  为角色添加账户
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  18:12 2019/8/10
    */
    void createApplyRoleAccount(ApplyRoleAccountDto applyRoleAccountDto);

    /**
    * @Description:  修改
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  14:40 2019/8/13
    */
    void modifyRoleApply(CreateRoleApplyDto createRoleApplyDto);

}
