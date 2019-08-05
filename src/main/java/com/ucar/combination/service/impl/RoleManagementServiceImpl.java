package com.ucar.combination.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.RoleManagementDao;
import com.ucar.combination.service.RoleManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Description: 角色管理
 *
 * @author gan.jin@ucarinc.com
 * @version 1.0
 * @Date 2019/7/31 16:55
 */
@Service
public class RoleManagementServiceImpl implements RoleManagementService {
    @Autowired
    private RoleManagementDao roleManagementDao;

    @Override
    public ResultPage queryList(QueryParam queryParam) {
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<Map<String, Object>> list = roleManagementDao.queryList(queryParam);
        System.out.println(list);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }

    @Override
    public void updateStatus(int id){
        roleManagementDao.updateStatus(id);
    }
}
