package com.ucar.combination.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.RoleApplyManageDao;
import com.ucar.combination.model.dto.RoleApplyDto;
import com.ucar.combination.service.RoleApplyManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description:角色申请管理
 *
 * @author min.zhang08@ucarinc.com
 * @date: Create in 9:39 now
 **/
@Service
public class RoleApplyManageServiceImpl implements RoleApplyManageService {
    @Autowired(required = false)
    private RoleApplyManageDao roleApplyManageDao;


    @Override
    public ResultPage queryList(QueryParam queryParam) {
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<RoleApplyDto> list = roleApplyManageDao.queryList(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }
    /**
     * description：导出角色申请列表
     * @author shiya.li@ucarinc.com
     * @param queryParam
     * @return
     */
    public List<RoleApplyDto> getRoleList(QueryParam queryParam){
        return roleApplyManageDao.queryList(queryParam);
    }



    /**
    * @Description:  删除
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  15:26 2019/8/7
    */
    @Override
    public void deleteRoleApply(String roleApplyNum) {
        roleApplyManageDao.deleteRoleApply(roleApplyNum);
    }

    /**
    * @Description:  提交审核
    * @Author: min.zhang08@ucarinc.com
    * @Params
    * @Return
    * @Date  17:41 2019/8/7
    */
    @Override
    public void commitRoleApply(String roleApplyNum) {
        roleApplyManageDao.commitRoleApply(roleApplyNum);

    }

}
