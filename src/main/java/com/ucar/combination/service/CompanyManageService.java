package com.ucar.combination.service;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.Company;
import com.ucar.combination.model.dto.BusinessLicense;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
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
    void insertCompany(MultipartFile[] file, String str, HttpSession session);

    /**
     * description: 根据公司id获取单一公司信息
     * @author: jianan.shu@ucarinc.com
     * @param:
     * @date: 2019/8/14 11:17
     * @return：
     */
    Map getCompanyById(int companyId);

    /**
     * description: 修改公司信息
     * @author: jianan.shu@ucarinc.com
     * @param: company
     * @date: 2019/8/8 9:05
     * @return：
     */
    void updateCompanyById(Company company);
    /**
     * description: 校验统一社会信用代码
     * @author: jianan.shu@ucarinc.com
     * @param:
     * @date: 2019/8/8 15:31
     * @return：
     */
    int creditCodeValidate(String creditCode);
    /**
     * description: 查询未关联公司
     * @author: jianan.shu@ucarinc.com
     * @param:
     * @date: 2019/8/14 11:17
     * @return：
     */
    ResultPage queryRelationList(QueryParam queryParam);
    /**
     * description: 获取关联公司列表
     * @author: jianan.shu@ucarinc.com
     * @param:
     * @date: 2019/8/14 11:17
     * @return：
     */
    ResultPage relationCompanyList(QueryParam queryParam);
    /**
     * description: 关联公司保存
     * @author: jianan.shu@ucarinc.com
     * @param:
     * @date: 2019/8/14 11:18
     * @return：
     */
    void saveRelations(Map<String, Object> queryParam);

    /**
     * description: 通过id找文件路径
     * @author: qingyu.lan@ucarinc.com
     * @param:
     * @date: 2019/8/14 15:23
     * @return：
     */
    String getFileUrlById(Long id);

    List<BusinessLicense> getIdsByCompanyId(int id);

}
