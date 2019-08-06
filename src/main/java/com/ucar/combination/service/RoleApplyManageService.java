package com.ucar.combination.service;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.dto.RoleApplyDto;

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

}
