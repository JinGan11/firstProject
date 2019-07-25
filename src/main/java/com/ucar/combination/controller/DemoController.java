package com.ucar.combination.controller;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.Result;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-25
 **/
@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @ResponseBody
    @RequestMapping("/list.do_")
    public Result list(HttpServletRequest request){
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String tableName = request.getParameter("tableName");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page",page);
        params.put("limit",limit);
        params.put("tableName",tableName);
        List<Map<String, Object>> list =demoService.queryList();
        return null;
    }

}
