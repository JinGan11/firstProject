package com.ucar.combination.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.DemoDao;
import com.ucar.combination.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * description:
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-25
 **/
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoDao demoDao;



//    @Override
//    public ResultPage queryList(QueryParam queryParam) {
//        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
//        List<Map<String, Object>> list = demoDao.queryList(queryParam);
//        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
//    }


    @Override
    public List<Map<String, Object>> queryList() {

        return demoDao.queryList();
    }


}
