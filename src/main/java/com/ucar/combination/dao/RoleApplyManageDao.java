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
}
