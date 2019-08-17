package com.ucar.combination.service;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.Region;
import com.ucar.combination.model.dto.RegionDto;

import java.util.List;
import java.util.Map;

/**
 * description:行政区划管理
 *
 * @author 杨锴伦（kailun.yang@ucarinc.com）
 * @version 1.0
 * @date 2019-08-06 16:04
 **/
public interface RegionManageService {
    /**
     * description: 省/市查询
     * @author kailun.yang@ucarinc.com
     * @date <2019-08-06>
     * @param <[queryParam]> <参数说明>
     * @return <com.ucar.combination.common.ResultPage><返回值说明>
     */
    ResultPage provinceSearchList(QueryParam queryParam);
    List<Object> getProvinceSearchList(QueryParam queryParam);

    /**
     * description: 城市查询
     * @author kailun.yang@ucarinc.com
     * @date <2019-08-07>
     * @param <[queryParam]> <参数说明>
     * @return <com.ucar.combination.common.ResultPage><返回值说明>
     */
    ResultPage citySearchList(QueryParam queryParam);
    List<Object> getCitySearchList(QueryParam queryParam);

    /**
     * description: 区县查询
     * @author kailun.yang@ucarinc.com
     * @date <2019-08-07>
     * @param <[queryParam]> <参数说明>
     * @return <com.ucar.combination.common.ResultPage><返回值说明>
     */
    ResultPage countySearchList(QueryParam queryParam);
    List<Object> getCountySearchList(QueryParam queryParam);


    /**
     * description: 新建省/直辖市
     * @author kailun.yang@ucarinc.com
     * @date <2019-08-12>
     * @param <[params]> <参数说明>
     * @return <java.lang.String><返回值说明>
     */
    int createRegion(Region region);

    /**
     * description: 新建修改页面，树
     * @author kailun.yang@ucarinc.com
     * @date <2019-08-17>
     * @param <[region]> <参数说明>
     * @return <java.util.List<com.ucar.combination.model.dto.RegionDto>><返回值说明>
     */
    List<RegionDto> getRegionList(Region region);

    /**
     * description: 根据查询条件返回区划数据
     * @author kailun.yang@ucarinc.com
     * @date <2019-08-17>
     * @param <[region]> <参数说明>
     * @return <java.util.List<com.ucar.combination.model.Region>><返回值说明>
     */
    Region getRegionDetails(Region region);

    int modifyRegion(Region region);

    /**
     * description: 用于得到所有城市的信息
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/14 15:02
     * @params
     * @return 返回一个城市的STRING集合
     */
    List<Object> getCityList();

    /**
     * description: 返回一个城市的集合
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/17 1:29
     * @params id 部门ID

     * @return
     */
    List<String> citySearchListById(String id);
}
