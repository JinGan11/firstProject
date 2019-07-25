package com.ucar.combination.service;


import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;

import java.util.List;
import java.util.Map;

/**
 * description:
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-25
 **/
public interface DemoService {


   // ResultPage queryList(QueryParam queryParam);
   List<Map<String, Object>> queryList();

}
