package com.ucar.combination.service;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
/**
 * description:公司管理
 *
 * @author jianan.shu@ucarinc.com
 * @version 1.0
 * @date: 2019/8/3 10:17
*/
public interface CompanyManageService {
    /**
     * description: 获取公司列表
     * @author: jianan.shu@ucarinc.com
     * @param: queryParam
     * @date: 2019/8/3 10:17
     * @return：
     */
    ResultPage queryList(QueryParam queryParam);
    /*
     * description: 部门查询获得对应的列表
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/6 10:14
     * @params queryParam CONTROLLER传过来的QUERYPARAM层

     * @return
     */
    ResultPage getCompanyList(QueryParam queryParam);
}
