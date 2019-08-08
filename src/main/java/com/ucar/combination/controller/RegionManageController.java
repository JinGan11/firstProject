package com.ucar.combination.controller;

import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.service.RegionManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:行政区划管理
 * @author 杨锴伦（kailun.yang@ucarinc.com）
 * @version 1.0
 * @date 2019-08-06 15:43
 **/
@Controller
@RequestMapping("/regionManage")
public class RegionManageController {
    @Autowired
    private RegionManageService regionManageService;

    /**
     * description: 省/市查询
     * @author kailun.yang@ucarinc.com
     * @date <2019-08-06>
     * @param <[request]> <参数说明>
     * @return <com.ucar.combination.common.Result><返回值说明>
     */
    @ResponseBody
    @RequestMapping("/provinceSearch")
    public Result provinceSearchList(HttpServletRequest request) throws IllegalAccessException{
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String regionCode = request.getParameter("regionCode");
        String regionName = request.getParameter("regionName");
        String regionStatus = request.getParameter("regionStatus");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        params.put("limit", limit);
        params.put("regionCode", regionCode);
        params.put("regionName", regionName);
        params.put("regionStatus", regionStatus);
        ResultPage resultPage=regionManageService.provinceSearchList(new QueryParam(params));
        List<Object> provinceSearchList = regionManageService.getProvinceSearchList(new QueryParam(params));
        return new Result().ok().put("page",resultPage).put("provinceSearchList",provinceSearchList).put("RegionStatus", CommonEnums.toEnumMap(CommonEnums.RegionStatus.values()));
    }

    /**
     * description: 城市查询
     * @author kailun.yang@ucarinc.com
     * @date <2019-08-07>
     * @param <[request]> <参数说明>
     * @return <com.ucar.combination.common.Result><返回值说明>
     */
    @ResponseBody
    @RequestMapping("/citySearch")
    public Result citySearchList(HttpServletRequest request) throws IllegalAccessException{
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String regionCode = request.getParameter("regionCode");
        String regionName = request.getParameter("regionName");
        String upperRegion = request.getParameter("upperRegion");
//        String upperRegionTwice = request.getParameter("upperRegionTwice");
        String regionStatus = request.getParameter("regionStatus");
        String upperRegionID = request.getParameter("upperRegionID");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        params.put("limit", limit);
        params.put("regionCode", regionCode);
        params.put("regionName", regionName);
        params.put("upperRegion",upperRegion);
        params.put("upperRegionID",upperRegionID);

//        params.put("upperRegionTwice",upperRegionTwice);
        params.put("regionStatus", regionStatus);
        ResultPage resultPage=regionManageService.citySearchList(new QueryParam(params));
        List<Object> citySearchList = regionManageService.getCitySearchList(new QueryParam(params));
        return new Result().ok().put("page",resultPage).put("citySearchList",citySearchList).put("RegionStatus", CommonEnums.toEnumMap(CommonEnums.RegionStatus.values()));
    }

    /**
     * description: 区县查询
     * @author kailun.yang@ucarinc.com
     * @date <2019-08-07>
     * @param <[request]> <参数说明>
     * @return <com.ucar.combination.common.Result><返回值说明>
     */
    @ResponseBody
    @RequestMapping("/countySearch")
    public Result countySearchList(HttpServletRequest request) throws IllegalAccessException{
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String regionCode = request.getParameter("regionCode");
        String regionName = request.getParameter("regionName");
        String upperRegion = request.getParameter("upperRegion");
        String upperRegionID = request.getParameter("upperRegionID");
        String regionStatus = request.getParameter("regionStatus");
        String upperRegionTwice = request.getParameter("upperRegionTwice");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        params.put("limit", limit);
        params.put("regionCode", regionCode);
        params.put("regionName", regionName);
        params.put("upperRegionTwice",upperRegionTwice);
        params.put("upperRegion",upperRegion);
        params.put("upperRegionID",upperRegionID);
        params.put("regionStatus", regionStatus);
        ResultPage resultPage=regionManageService.countySearchList(new QueryParam(params));
        List<Object> countySearchList = regionManageService.getCountySearchList(new QueryParam(params));
        return new Result().ok().put("page",resultPage).put("countySearchList",countySearchList).put("RegionStatus", CommonEnums.toEnumMap(CommonEnums.RegionStatus.values()));
    }


    @ResponseBody
    @RequestMapping("/createProvince")
    public Result createProvince(HttpServletRequest request) throws  IllegalAccessException{


        String regionCode = request.getParameter("regionCode");
        String regionName = request.getParameter("regionName");
        String regionPinyin = request.getParameter("regionPinyin");
        String upperRegion = request.getParameter("upperRegion");
        String regionStatus = request.getParameter("regionStatus");
        String mEmp = request.getParameter("mEmp");
        String mTime = request.getParameter("mTime");



        Map<String,Object> params=new HashMap<>();
        params.put("regionCode", regionCode);
        params.put("regionName", regionName);
        params.put("regionPinyin",regionPinyin);
        params.put("upperRegion",upperRegion);
        params.put("regionStatus", regionStatus);
        params.put("mEmp",mEmp);
        params.put("mTime", mTime);

        for(String key:params.keySet()){
            System.out.println("key: "+key+" value: "+params.get(key));
        }

//        return new Result().ok().put("msg",map);
        return null;
    }
}
