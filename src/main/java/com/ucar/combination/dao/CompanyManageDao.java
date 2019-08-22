package com.ucar.combination.dao;

import com.ucar.combination.model.Account;
import com.ucar.combination.model.Company;
import com.ucar.combination.model.dto.BusinessLicense;
import com.ucar.combination.model.dto.CompanyDto;
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
     * @return：list 公司列表
     */
    List<Company> queryList(Map<String, Object> map);
    /**
     * description: 部门查询的查询公司列表
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/6 10:24
     * @params map 描述

     * @return
     */
    List<Company> getCompanyList(Map<String, Object> map);
    /**
     * description: 插入公司信息
     * @author: jianan.shu@ucarinc.com
     * @param: company 公司信息
     * @date: 2019/8/6 15:17
     * @return：
     */
    Long insertCompany(Company company);
    /**
     * description: 得到修改的公司信息
     * @author: jianan.shu@ucarinc.com
     * @param: id 公司id
     * @date: 2019/8/7 14:07
     * @return： company 公司类对象
     */
    Company getCompanyById(@Param("companyId") int companyId);
    /**
     * description: 修改公司信息
     * @author: jianan.shu@ucarinc.com
     * @param: company 公司对象
     * @date: 2019/8/8 9:08
     * @return：
     */
    void updateCompanyById(Company company);
    /**
     * description: 获取账户姓名
     * @author: jianan.shu@ucarinc.com
     * @param: accountId 账号id
     * @date: 2019/8/8 12:30
     * @return： 账号姓名
     */
    String getEmpById(long accountId);
    /**
     * description: 统一社会信用代码校验
     * @author: jianan.shu@ucarinc.com
     * @param: creditCode
     * @date: 2019/8/8 15:32
     * @return： 记录个数
     */
    Integer creditCodeValidate(@Param("creditCode") String creditCode);
    /**
     * description: 查询未关联到部门的公司
     * @author: jianan.shu@ucarinc.com
     * @param: map
     * @date: 2019/8/14 11:13
     * @return： companyDto对象
     */
    List<CompanyDto> queryRelationList(Map<String, Object> map);
    /**
     * description: 获取和部门关联的公司
     * @author: jianan.shu@ucarinc.com
     * @param:  map
     * @date: 2019/8/14 11:13
     * @return：list companyDto列表
     */
    List<CompanyDto>relationCompanyList(Map<String, Object> map);
    /**
     * description: 添加关联公司
     * @author: jianan.shu@ucarinc.com
     * @param: map
     * @date: 2019/8/14 11:14
     * @return：
     */
    void addRelationCompany(Map<String, Object> map);
    /**
     * description: 更新关联公司
     * @author: jianan.shu@ucarinc.com
     * @param: map
     * @date: 2019/8/14 11:14
     * @return：
     */
    void updateRelation(Map<String, Object> map);
    /**
     * description: 得知表中是否已有该关联公司
     * @author: jianan.shu@ucarinc.com
     * @param: map
     * @date: 2019/8/14 11:14
     * @return： 记录个数
     */
    Integer getRelationCount(Map<String, Object> map);

    /**
     * description: 插入文件的存储路径
     * @author: qingyu.lan@ucarinc.com
     * @param:
     * @date: 2019/8/13 15:23
     * @return：
     */
    void insertLicenses(List list);
    Map<String,Object> getCreateInfo(@Param("companyId") int companyId);
    Map<String,Object> getModifyInfo(@Param("companyId") int companyId);

    /**
     * description: 通过id找文件路径
     * @author: qingyu.lan@ucarinc.com
     * @param:
     * @date: 2019/8/14 15:23
     * @return：
     */
    String getFileUrlById(Long id);

    List<BusinessLicense> getIdsByCompanyId(int id);
    /**
     * description: 方法描述信息
     * @author: jianan.shu@ucarinc.com
     * @param: id 公司id
     * @date: 2019/8/22 12:49
     * @return： 创建人账号id
     */
    Long getCreateStaffId(@Param("companyId")int id);
    /**
     * description: 方法描述信息
     * @author: jianan.shu@ucarinc.com
     * @param: id 公司id
     * @date: 2019/8/22 12:49
     * @return： 修改人账号id
     */
    Long getModifyStaffId(@Param("companyId")int id);

    void deleteLicenseById(Long id);
}
