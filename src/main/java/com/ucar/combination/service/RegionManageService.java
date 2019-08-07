package com.ucar.combination.service;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;

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
    String testSer();
}
