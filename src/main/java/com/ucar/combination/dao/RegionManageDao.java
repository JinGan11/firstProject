package com.ucar.combination.dao;

import com.ucar.combination.model.Region;
import com.ucar.combination.model.dto.RegionDto;

import java.util.List;
import java.util.Map;

/**
 * description:行政区划管理
 *
 * @author 杨锴伦（kailun.yang@ucarinc.com）
 * @version 1.0
 * @date 2019-08-06 16:24
 **/
public interface RegionManageDao {

    /**
     * description: 省/市查询
     * @author kailun.yang@ucarinc.com
     * @date <2019-08-06>
     * @param <[map]> <参数说明>
     * @return <java.util.List<java.lang.Object>><返回值说明>
     */
    List<Object> provinceSearchList(Map<String, Object> map);

    /**
     * description: 城市查询
     * @author kailun.yang@ucarinc.com
     * @date <2019-08-07>
     * @param <[map]> <参数说明>
     * @return <java.util.List<java.lang.Object>><返回值说明>
     */
    List<Object> citySearchList(Map<String, Object> map);

    /**
     * description: 区县查询
     * @author kailun.yang@ucarinc.com
     * @date <2019-08-07>
     * @param <[map]> <参数说明>
     * @return <java.util.List<java.lang.Object>><返回值说明>
     */
    List<Object> countySearchList(Map<String, Object> map);

    int createProvince(Region region);

    /**
     * description: 新建修改页面树节点数据获取
     * @author kailun.yang@ucarinc.com
     * @date <2019-08-17>
     * @param <[region]> <参数说明>
     * @return <java.util.List<com.ucar.combination.model.dto.RegionDto>><返回值说明>
     */
    List<RegionDto> getRegionList(Region region);

    /**
     * description: 根据查询条件获取区划信息
     * @author kailun.yang@ucarinc.com
     * @date <2019-08-17>
     * @param <[region]> <参数说明>
     * @return <java.util.List<com.ucar.combination.model.Region>><返回值说明>
     */
    Region getRegionDetails(Region region);

    List<Object> getCityList() ;

}
