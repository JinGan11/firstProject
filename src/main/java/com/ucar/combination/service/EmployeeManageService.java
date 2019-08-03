package com.ucar.combination.service;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;

import java.util.List;


/**
 * description:员工管理
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-27 17:23:44
 **/
public interface EmployeeManageService {
    /**
     * 员工管理列表
     * @param queryParam
     * @return
     */
    ResultPage queryList(QueryParam queryParam);

    /**
     * 导出员工列表
     * @param queryParam
     * @return
     */
    public List<Object> getStaffList(QueryParam queryParam);
}
