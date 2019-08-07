package com.ucar.combination.dao;
import com.ucar.combination.model.dto.RoleApplyDto;

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
    void deleteRoleApply(String roleApplyNum);

    /**
    * @Description:  提交审核
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  17:39 2019/8/7
    */
    void commitRoleApply(String roleApplyNum);
}
