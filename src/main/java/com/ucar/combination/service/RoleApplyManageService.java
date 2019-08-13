package com.ucar.combination.service;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.dto.*;

import java.util.List;

/**
 * description:
 *
 * @author min.zhang08@ucarinc.com
 * @date: Create in 9:37 now
 **/
public interface RoleApplyManageService {
    /**
    * @Description:  角色申请管理列表
    * @Author
    * @Params
    * @Return
    * @Date  9:38 2019/8/3
    */

    ResultPage queryList(QueryParam queryParam);

    /**
     *
     * @param queryParam
     * @return
     */
    public List<RoleApplyDto> getRoleList(QueryParam queryParam);

    /**
    * @Description:  删除
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  15:25 2019/8/7
    */
    void deleteRoleApply(int id);

    /**
    * @Description:  提交审核
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  17:40 2019/8/7
    */
    void commitRoleApply(int id);

    /**
    * @Description:
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  15:41 2019/8/8
    */
    void createRoleApply(CreateRoleApplyDto createRoleApplyDto);

    /**
    * @Description: 角色申请 修改页面的账户列表
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  11:08 2019/8/9
    */
    List<AccountListByApplyIdDto> showAccountListByApplyId(Long applyId);

    /**
    * @Description:  角色申请  获取当前登录信息的
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  20:35 2019/8/9
    */
    LoginInfoInRoleApplyDto queryLoginInRoleApply(String applyAccountName);


    /**
    * @Description:  角色申请 新建 为角色添加账户
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  18:17 2019/8/10
    */
    void createApplyRoleAccount(ApplyRoleAccountDto applyRoleAccountDto);


    /**
    * @Description:  修改
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  14:41 2019/8/13
    */
    void modifyRoleApply(CreateRoleApplyDto createRoleApplyDto);

}
