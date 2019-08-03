package com.ucar.combination.dao;

import com.ucar.combination.model.Account;
import com.ucar.combination.model.Company;

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
}
