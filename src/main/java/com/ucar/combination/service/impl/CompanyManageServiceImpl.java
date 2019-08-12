package com.ucar.combination.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.CompanyManageDao;
import com.ucar.combination.model.Company;
import com.ucar.combination.service.CompanyManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void insertCompany(Company company){
        System.out.println(company.getBusinessStartTime());
        companyManageDao.insertCompany(company);
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
        String createEmp=companyManageDao.getEmpById(company.getCreateEmp());
        String modifyEmp=companyManageDao.getEmpById(company.getModifyEmp());
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
    @Override
    public ResultPage queryRelationList(QueryParam queryParam){
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<Company> list = companyManageDao.queryRelationList(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }
    public ResultPage relationCompanyList(QueryParam queryParam){
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<Company> list = companyManageDao.relationCompanyList(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }
    public void saveRelations(Map<String ,Object>queryParam){
        List oldRelationCompany=(List<Long>)queryParam.get("oldRelationList");
        List newRelationCompany= (List<Long>) queryParam.get("newRelationList");
        String departmentId=(String)queryParam.get("departmentId");
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
                if(count == null){
                    companyManageDao.addRelationCompany(map);;
                }else{
                    map.put("status",1);
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
                companyManageDao.updateRelation(relMap);
            }
        }

    }
}

