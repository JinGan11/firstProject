package com.ucar.combination.service;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;

import java.util.List;

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



    String testSer();
}
