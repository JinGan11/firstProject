package com.ucar.combination.dao;

import com.ucar.combination.model.Account;
import com.ucar.combination.model.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * description:公司管理
 *
 * @author jianan.shu@ucarinc.com
 * @version 1.0
 * @date: 2019/8/3 9:32
*/
public interface CompanyManageDao<mcmt> {
    /**
     * description: 获取公司列表
     * @author: jianan.shu@ucarinc.com
     * @param: map
     * @date: 2019/8/3 9:30
     * @return：公司列表
     */
    List<Company> queryList(Map<String, Object> map);
    /**
     * description: 部门查询的查询公司列表
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/6 10:24
     * @params map 描述

     * @return
     */
    List<Company> getCompanyList(Map<String,Object> map);
    /**
     * description: 插入公司信息
     * @author: jianan.shu@ucarinc.com
     * @param:
     * @date: 2019/8/6 15:17
     * @return：
     */
    void insertCompany(Company company);
    /**
     * description: 得到修改的公司信息
     * @author: jianan.shu@ucarinc.com
     * @param: id
     * @date: 2019/8/7 14:07
     * @return：
     */
    Company getCompanyById(@Param("companyId") int companyId);
}
