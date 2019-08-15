package com.ucar.combination.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.CompanyManageDao;
import com.ucar.combination.model.Company;
import com.ucar.combination.model.dto.BusinessLicense;
import com.ucar.combination.service.CompanyManageService;
import com.ucar.combination.utils.FileUrlGenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @author jianan.shu@ucarinc.com
 * @version 1.0
 * @date: 2019/8/3 10:19
*/
@Service
public class CompanyManageServiceImpl<updateCompanyById> implements CompanyManageService {
    @Autowired(required = false)
    private CompanyManageDao companyManageDao;
    @Override
    public ResultPage queryList(QueryParam queryParam) {
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<Company> list = companyManageDao.queryList(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }
    /**
     * description: 部门查询里面详细页查找详细公司所用方法
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/5 20:03
     * @params queryParam CONTROLLER传过来的QUERYPARAM对象

     * @return
     */
    @Override
    public ResultPage getCompanyList(QueryParam queryParam) {
       Page<?> page=PageHelper.startPage(queryParam.getPage(),queryParam.getLimit());
       List<Company> list=companyManageDao.getCompanyList(queryParam);
       return new ResultPage(list,(int)page.getTotal(),queryParam.getLimit(),queryParam.getPage());
    }
    /**
     * description: 插入公司信息
     * @author: jianan.shu@ucarinc.com
     * @param: company
     * @date: 2019/8/6 15:16
     * @return：
     */
    @Override
    public void insertCompany(MultipartFile[] file, String str, HttpSession session){
        Company company = JSON.parseObject(str, Company.class);
        Long accountId = (Long) session.getAttribute("accountId");
        company.setCreateEmp(accountId);
        company.setModifyEmp(accountId);
        companyManageDao.insertCompany(company);
        Long id =company.getCompanyId();
        if (file.length != 0) {
            List<BusinessLicense> list = new ArrayList<>();
            for (int i = 0; i < file.length; i++) {
                String url = FileUrlGenUtils.generateUrl(file[i]);
                BusinessLicense businessLicense = new BusinessLicense();
                businessLicense.setCompanyId(id);
                businessLicense.setFileLocation(url);
                list.add(businessLicense);
            }
            companyManageDao.insertLicenses(list);
            for (int i = 0; i < file.length; i++) {
                File license = new File(list.get(i).getFileLocation());
                try {
                    file[i].transferTo(license);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * description: 依据公司Id获取公司信息
     * @author: jianan.shu@ucarinc.com
     * @param:
     * @date: 2019/8/8 9:25
     * @return：
     */

    public Map getCompanyById(int companyId){
        Company company=companyManageDao.getCompanyById(companyId);
        //String createEmp=companyManageDao.getEmpById(company.getCreateEmp());
        //String modifyEmp=companyManageDao.getEmpById(company.getModifyEmp());
        Map<String,Object>createMap=companyManageDao.getCreateInfo(companyId);
        Map<String,Object>modifyMap=companyManageDao.getModifyInfo(companyId);
        String createAccountName=(String)createMap.get("accountName");
        String createStaffName=(String)createMap.get("staffName");
        String modifyAccountName=(String)modifyMap.get("accountName");
        String modifyStaffName=(String)modifyMap.get("staffName");
        String createEmp=createAccountName+"("+createStaffName+")";
        String modifyEmp=modifyAccountName+"("+modifyStaffName+")";
        Map<String,Object> resultMap=new HashMap<String, Object>();
        resultMap.put("company",company);
        resultMap.put("createEmp",createEmp);
        resultMap.put("modifyEmp",modifyEmp);
        return resultMap;
    }
    /**
     * description: 修改公司信息
     * @author: jianan.shu@ucarinc.com
     * @param:
     * @date: 2019/8/8 9:26
     * @return：
     */

    public void updateCompanyById(Company company){
        companyManageDao.updateCompanyById(company);
    }
    /**
     * description: 校验统一社会信用代码
     * @author: jianan.shu@ucarinc.com
     * @param:
     * @date: 2019/8/8 15:36
     * @return：
     */
    public int creditCodeValidate(String creditCode){
        Integer validate ;
        validate = companyManageDao.creditCodeValidate(creditCode);
        if(validate == null){
            return 0;
        }
        return validate.intValue();
    }
    /**
     * description: 查询未关联公司
     * @author: jianan.shu@ucarinc.com
     * @param:
     * @date: 2019/8/14 11:15
     * @return：
     */
    @Override
    public ResultPage queryRelationList(QueryParam queryParam){
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<Company> list = companyManageDao.queryRelationList(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }
    /**
     * description: 关联公司
     * @author: jianan.shu@ucarinc.com
     * @param:
     * @date: 2019/8/14 11:16
     * @return：
     */
    public ResultPage relationCompanyList(QueryParam queryParam){
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<Company> list = companyManageDao.relationCompanyList(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }
    /**
     * description: 关联公司保存
     * @author: jianan.shu@ucarinc.com
     * @param:
     * @date: 2019/8/14 11:16
     * @return：
     */
    public void saveRelations(Map<String ,Object>queryParam){
        List oldRelationCompany=(List<Long>)queryParam.get("oldRelationList");
        List newRelationCompany= (List<Long>) queryParam.get("newRelationList");
        String departmentId=(String)queryParam.get("departmentId");
        long accountId=(long)queryParam.get("accountId");
        //添加、不变
        for(int i=0;i<newRelationCompany.size();i++){
            int flag=0;
            for(int j=0;j<oldRelationCompany.size();j++){
                if(newRelationCompany.get(i).equals(oldRelationCompany.get(j))){
                    flag=1;
                }
            }
            //如果原有的关联公司没有新公司，则添加   1、原先没有记录 2、原先有记录，将status变为有效（1）
            if(flag==0){
                Map<String,Object>map=new HashMap<>();
                map.put("companyId",newRelationCompany.get(i));
                map.put("departmentId",departmentId);
                Integer count ;
                count = companyManageDao.getRelationCount(map);
                if(count == 0){
                    map.put("createEmp",accountId);
                    map.put("modifyEmp",accountId);
                    companyManageDao.addRelationCompany(map);;
                }else{
                    map.put("status",1);
                    map.put("modifyEmp",accountId);
                    companyManageDao.updateRelation(map);

                }
            }
        }
        //删除
        for(int j=0;j<oldRelationCompany.size();j++){
            int flag=0;
            for (int i = 0; i < newRelationCompany.size(); i++) {
                if(oldRelationCompany.get(j).equals(newRelationCompany.get(i))){
                    flag=1;
                }
            }
            if(flag==0){
                Map<String,Object>relMap=new HashMap<>();
                relMap.put("departmentId",departmentId);
                relMap.put("companyId",oldRelationCompany.get(j));
                relMap.put("status",2);
                relMap.put("modifyEmp",accountId);
                companyManageDao.updateRelation(relMap);
            }
        }
    }

    @Override
    public String getFileUrlById(Long id) {
        return companyManageDao.getFileUrlById(id);
    }

    @Override
    public List<BusinessLicense> getIdsByCompanyId(int id) {
        return companyManageDao.getIdsByCompanyId(id);
    }
}

