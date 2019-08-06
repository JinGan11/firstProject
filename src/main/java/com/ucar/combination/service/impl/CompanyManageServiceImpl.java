package com.ucar.combination.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.CompanyManageDao;
import com.ucar.combination.dao.EmployeeManageDao;
import com.ucar.combination.model.Company;
import com.ucar.combination.model.dto.StaffDto;
import com.ucar.combination.service.CompanyManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description:
 *
 * @author jianan.shu@ucarinc.com
 * @version 1.0
 * @date: 2019/8/3 10:19
*/
@Service
public class CompanyManageServiceImpl implements CompanyManageService {
    @Autowired(required = false)
    private CompanyManageDao companyManageDao;
    @Override
    public ResultPage queryList(QueryParam queryParam) {
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<Company> list = companyManageDao.queryList(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }
    /*
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
        companyManageDao.insertCompany(company);
    }
}

