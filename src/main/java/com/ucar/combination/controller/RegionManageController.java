package com.ucar.combination.controller;

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
import java.util.Map;

/**
 * description:行政区划管理
 *
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

        for (String key:params.keySet()) {
            System.out.println("key: "+key+" value: "+params.get(key));
        }

        ResultPage resultPage=regionManageService.provinceSearchList(new QueryParam(params));



//return null;
        return new Result().ok().put("page",resultPage);
    }
}
