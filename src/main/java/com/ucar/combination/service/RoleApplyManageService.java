package com.ucar.combination.service;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import org.springframework.stereotype.Service;


public interface RoleApplyManageService {
    /**
     * 角色申请管理列表
     * @param queryParam
     * @return
     */
    ResultPage queryList(QueryParam queryParam);
}
