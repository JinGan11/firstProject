package com.ucar.combination.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.RegionManageDao;
import com.ucar.combination.model.Region;
import com.ucar.combination.model.dto.RegionDto;
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
    public int createRegion(Region region) {
        return regionManageDao.createRegion(region);
    }

    @Override
    public List<RegionDto> getRegionList(Region region) {
        List<RegionDto> regionList = regionManageDao.getRegionList(region);
        if(region.getRegionLevel()==3){
            for(RegionDto regionDto:regionList){
                regionDto.setRegionLeaf(true);
            }
        }
        return regionList;
    }

    @Override
    public Region getRegionDetails(Region region) {
        return regionManageDao.getRegionDetails(region);
    }

    @Override
    public int modifyRegion(Region region) {
        return regionManageDao.modifyRegion(region);
    }

    @Override
    public List<Region> matchRegionCode(Region region) {
        return regionManageDao.matchRegionCode(region);
    }

    @Override
    public Region getRegionCodeByCityID(Region region) {
        return regionManageDao.getRegionCodeByCityID(region);
    }

    @Override
    public List<Region> getRegionByUpperCityID(Region region) {
        return regionManageDao.getRegionByUpperCityID(region);
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
    public List<Object> getCityList() {
        return regionManageDao.getCityList();
    }

    @Override
    public List<String> citySearchListById(String id) {
        return regionManageDao.citySearchListById(id);
    }
}
