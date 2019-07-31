package com.ucar.combination.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.RoleApplyManageDao;
import com.ucar.combination.service.RoleApplyManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class RoleApplyManageServiceImpl implements RoleApplyManageService {

    @Autowired
    private RoleApplyManageDao roleApplyManageDao;

    @Override
    public ResultPage queryList(QueryParam queryParam) {
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<Map<String, Object>> list = roleApplyManageDao.queryList(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }
}
