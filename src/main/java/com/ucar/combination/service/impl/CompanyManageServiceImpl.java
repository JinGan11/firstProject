package com.ucar.combination.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.CompanyManageDao;
import com.ucar.combination.model.Company;
import com.ucar.combination.model.dto.BusinessLicense;
import com.ucar.combination.model.dto.CompanyDto;
import com.ucar.combination.service.CompanyManageService;
import com.ucar.combination.service.DepartmentService;
import com.ucar.combination.utils.FileUrlGenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private DepartmentService departmentService;
    /**
     * description: 方法描述信息
     * @author: jianan.shu@ucarinc.com
     * @param: queryParam 公司查询条件
     * @date: 2019/8/22 13:35
     * @return：resultPage 分页查询公司的结果集
     */
    @Override
    public ResultPage queryList(QueryParam queryParam) {
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<CompanyDto> list = companyManageDao.queryList(queryParam);

        //修改人格式
        for(int i=0;i<list.size();i++) {
            if (companyManageDao.getModifyStaffId(list.get(i).getId()) != null && companyManageDao.getModifyStaffId(list.get(i).getId()) > 0) {
                Map<String, Object> map = new HashMap<>();
                //int long格式不对
                map = companyManageDao.getModifyInfo(list.get(i).getId());
                list.get(i).setModifyName(map.get("accountName") + "(" + map.get("staffName") + ")");
            }
        }
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
     * @param: companyId 公司id
     * @date: 2019/8/8 9:25
     * @return： map 根据公司id获取的公司信息
     */

    public Map getCompanyById(Long companyId){
        Company company=companyManageDao.getCompanyById(companyId);
        Long createStaffId=companyManageDao.getCreateStaffId(companyId);
        Long modifyStaffId=companyManageDao.getModifyStaffId(companyId);
        Map<String,Object>createMap=new HashMap<>();
        Map<String,Object>modifyMap=new HashMap<>();
        String createEmp="";
        String modifyEmp="";

        //判断账号是否关联员工
        if(createStaffId==null||createStaffId==0){
            String createAccountName=companyManageDao.getEmpById(company.getCreateEmp());
            createEmp=createAccountName;
        }else{
            createMap=companyManageDao.getCreateInfo(companyId);
            String createAccountName=(String)createMap.get("accountName");
            String createStaffName=(String)createMap.get("staffName");
            createEmp=createAccountName+"("+createStaffName+")";
        }

        if(modifyStaffId==null||modifyStaffId==0){
            String modifyAccountName=companyManageDao.getEmpById(company.getModifyEmp());
            modifyEmp=modifyAccountName;
        }else{
            modifyMap=companyManageDao.getModifyInfo(companyId);
            String modifyAccountName=(String)modifyMap.get("accountName");
            String modifyStaffName=(String)modifyMap.get("staffName");
            modifyEmp=modifyAccountName+"("+modifyStaffName+")";
        }
        Map<String,Object> resultMap=new HashMap<String, Object>();
        resultMap.put("company",company);
        resultMap.put("createEmp",createEmp);
        resultMap.put("modifyEmp",modifyEmp);
        return resultMap;
    }
    /**
     * description: 修改公司信息
     * @author: jianan.shu@ucarinc.com
     * @param: file 附件地址信息
     * @param: str 修改的公司信息
     * @date: 2019/8/8 9:26
     * @return：
     */

    public void updateCompanyById(MultipartFile[] file, String str, HttpSession session){
        Company company = JSON.parseObject(str, Company.class);
        Long accountId = (Long) session.getAttribute("accountId");
        company.setModifyEmp(accountId);
        companyManageDao.updateCompanyById(company);
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
     * description: 校验统一社会信用代码
     * @author: jianan.shu@ucarinc.com
     * @param: creditCode 社会信用代码
     * @date: 2019/8/8 15:36
     * @return： map 校验结果
     */
    public Map<String, Object> creditCodeValidate(String creditCode){
        Map<String, Object> map = new HashMap<>();
        map.put("result", true);
        Integer validate ;
        validate = companyManageDao.creditCodeValidate(creditCode);
        if(validate>0){
            map.put("result", false);
            map.put("msg", "统一社会信用代码已存在！");
        }
        return map;
    }
    /**
     * description: 查询未关联公司
     * @author: jianan.shu@ucarinc.com
     * @param: queryParam 查询未关联公司的条件
     * @date: 2019/8/14 11:15
     * @return： resultPage 查询未关联公司的结果
     */
    @Override
    public ResultPage queryRelationList(QueryParam queryParam){
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<CompanyDto> list = companyManageDao.queryRelationList(queryParam);
        //修改人样式
        for(int i=0;i<list.size();i++)
            if (companyManageDao.getModifyStaffId(list.get(i).getId())!=null&&companyManageDao.getModifyStaffId(list.get(i).getId()) > 0) {
                Map<String, Object> map = new HashMap<>();
                //int long格式不对
                map = companyManageDao.getModifyInfo(list.get(i).getId());
                list.get(i).setModifyName(map.get("accountName") + "(" + map.get("staffName") + ")");
            }
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }
    /**
     * description: 获取部门的关联公司
     * @author: jianan.shu@ucarinc.com
     * @param: map 包含部门信息
     * @date: 2019/8/14 11:16
     * @return： list 关联公司列表
     */
    public List<CompanyDto> relationCompanyList(Map<String,Object>map){
        List<CompanyDto> list = companyManageDao.relationCompanyList(map);
        //修改人样式
        for(int i=0;i<list.size();i++)
            if (companyManageDao.getModifyStaffId(list.get(i).getId())!=null&&companyManageDao.getModifyStaffId(list.get(i).getId()) > 0) {
                Map<String, Object> resultMap = new HashMap<>();
                //int long格式不对
                resultMap = companyManageDao.getModifyInfo(list.get(i).getId());
                list.get(i).setModifyName(resultMap.get("accountName") + "(" + resultMap.get("staffName") + ")");
            }
        return  list;
    }
    /**
     * description: 关联公司保存
     * @author: jianan.shu@ucarinc.com
     * @param: queryParam
     * @date: 2019/8/14 11:16
     * @return： map
     */
    public Map<String,Object> saveRelations(Map<String ,Object>queryParam){
        Map<String,Object>resultMap=new HashMap<>();
        resultMap.put("code",200);
        List oldRelationCompany=(List<Long>)queryParam.get("oldRelationList");
        List newRelationCompany=(List<Long>) queryParam.get("newRelationList");
        String departmentId=(String)queryParam.get("departmentId");
        long accountId=(long)queryParam.get("accountId");
        if(oldRelationCompany.get(0).equals("")) oldRelationCompany.remove(0);
        if(newRelationCompany.get(0).equals("")) newRelationCompany.remove(0);
        //部门已经被删除
        if(!departmentService.checkStatusChange(Long.parseLong(departmentId),null)){
            resultMap.put("code",501);
        }else{
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
                        map.put("status",1);
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
        return resultMap;
    }

    @Override
    public String getFileUrlById(Long id) {
        return companyManageDao.getFileUrlById(id);
    }

    @Override
    public List<BusinessLicense> getIdsByCompanyId(Long id) {
        return companyManageDao.getIdsByCompanyId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLicense(Long id){
        String fileUrl = companyManageDao.getFileUrlById(id);
        try {
            companyManageDao.deleteLicenseById(id);
            File file = new File(fileUrl);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

