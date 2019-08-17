package com.ucar.combination.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ucar.combination.common.CommonEnums;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.model.Region;
import com.ucar.combination.model.dto.RegionDto;
import com.ucar.combination.service.RegionManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.ImageProducer;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
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


    /**
     * description: 新建省/直辖市
     * @author kailun.yang@ucarinc.com
     * @date <2019-08-12>
     * @param <[request]> <参数说明>
     * @return <com.ucar.combination.common.Result><返回值说明>
     */
    @ResponseBody
    @RequestMapping("/createProvince")
    public String createProvince(HttpServletRequest request, HttpSession session) throws IllegalAccessException, ParseException {
        String mTime =request.getParameter("modifyTime");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date modifyTime = formatter.parse(mTime);
//        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        Long accountId = (Long) session.getAttribute("accountId");
        Region region=new Region();


        region.setRegionCode(request.getParameter("regionCode"));
        region.setRegionName(request.getParameter("regionName"));
        region.setRegionPinyin(request.getParameter("regionPinyin"));
        region.setRegionLevel(Integer.valueOf(request.getParameter("regionLevel")));
        region.setRegionStatus(Integer.valueOf(request.getParameter("regionStatus")));
        region.setUpperRegion(request.getParameter("upperRegion"));


        region.setCreateEmp(accountId);
        region.setCreateTime(modifyTime);
        region.setModifyEmp(accountId);
        region.setModifyTime(modifyTime);
        region.setRemark("无情");

//        regionManageService.createProvince(region);

        int resultValue=regionManageService.createProvince(region);
//        System.out.println("resultValue : "+resultValue);
        if(resultValue==1)
            {return "success";}
        else
            {return "false";}
    }
    /**
     * description: 部门及部门下属城市列表展示
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/17 12:01
     * @params id 描述departmentId

     * @return
     */
    @ResponseBody
    @RequestMapping("/citySearchListById.do_")
    public Result citySearchListById(@RequestParam String id){
        List<String> cityList=regionManageService.citySearchListById(id);
        return Result.ok().put("cityList",cityList);
    }
}
