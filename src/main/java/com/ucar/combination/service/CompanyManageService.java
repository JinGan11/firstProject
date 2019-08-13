package com.ucar.combination.service;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.Company;

import java.util.Map;

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
    /**
     * description: 插入公司信息
     * @author: jianan.shu@ucarinc.com
     * @param: company
     * @date: 2019/8/6 15:12
     * @return：
     */
    void insertCompany(Company company);


    Map getCompanyById(int companyId);

    /**
     * description: 修改公司信息
     * @author: jianan.shu@ucarinc.com
     * @param: company
     * @date: 2019/8/8 9:05
     * @return：
     */
    Map<String,Object> updateCompanyById(Company company);
    /**
     * description: 校验统一社会信用代码
     * @author: jianan.shu@ucarinc.com
     * @param:
     * @date: 2019/8/8 15:31
     * @return：
     */
    Map<String, Object> creditCodeValidate(String creditCode);
    ResultPage queryRelationList(QueryParam queryParam);
    ResultPage relationCompanyList(QueryParam queryParam);
    void saveRelations(Map<String ,Object>queryParam);

}
