package com.ucar.combination.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.RegionManageDao;
import com.ucar.combination.model.Region;
import com.ucar.combination.service.RegionManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @author 杨锴伦（kailun.yang@ucarinc.com）
 * @version 1.0
 * @date 2019-08-06 16:09
 **/
@Service
public class RegionManageServiceImpl implements RegionManageService {
    @Autowired(required = false)
    private RegionManageDao regionManageDao;

    @Override
    public ResultPage provinceSearchList(QueryParam queryParam) {
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<Object> list = regionManageDao.provinceSearchList(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }

    @Override
    public List<Object> getProvinceSearchList(QueryParam queryParam) {
        return regionManageDao.provinceSearchList(queryParam);
    }

    @Override
    public List<Object> getCitySearchList(QueryParam queryParam) {
        return regionManageDao.citySearchList(queryParam);
    }

    @Override
    public List<Object> getCountySearchList(QueryParam queryParam) {
        return regionManageDao.countySearchList(queryParam);
    }

    @Override
    public int createProvince(Region region) {
        return regionManageDao.createProvince(region);
    }

    @Override
    public ResultPage citySearchList(QueryParam queryParam) {
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<Object> list = regionManageDao.citySearchList(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }

    @Override
    public ResultPage countySearchList(QueryParam queryParam) {
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<Object> list = regionManageDao.countySearchList(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }
    @Override
    public String testSer() {
        return "this is service";
    }
}
