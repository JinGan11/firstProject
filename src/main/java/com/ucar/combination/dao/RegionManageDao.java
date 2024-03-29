package com.ucar.combination.dao;

import com.ucar.combination.model.Region;
import com.ucar.combination.model.dto.RegionDto;
import com.ucar.combination.model.dto.RegionSimpleDto;
import org.apache.ibatis.annotations.Param;

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

    int createRegion(Region region);

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

    int modifyRegion(Region region);
    int modifySubRegion(Region region);

    List<Region> matchRegionCode(Region region);
    List<Region> getRegionByUpperCityID(Region region);

    Region getRegionCodeByCityID(Region region);

    List<Object> getCityList() ;
    /**
     * description: 返回部门即下属部门所有城市
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/17 1:32
     * @params id 部门ID

     * @return
     */
    List<String> citySearchListById(String id);

    List<String> citySearchListById1(String id);

    /**
     * description: 根据关键词查找城市
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/22 15:15
     * @params
     * @return
     */
    List<RegionSimpleDto> selectCityByKeyword(@Param(value="keyword")String keyword);
}
