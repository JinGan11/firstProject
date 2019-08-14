package com.ucar.combination.service.impl;

import com.ucar.combination.dao.RoleApplyApprovalDao;
import com.ucar.combination.service.RoleApplyApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RoleApplyApprovalServiceImpl implements RoleApplyApprovalService {

    @Autowired(required = false)
    private RoleApplyApprovalDao roleApplyApprovalDao;

    @Override
    public void approvalPass(Map<String, Object> map) {
        roleApplyApprovalDao.approvalPass(map);
        roleApplyApprovalDao.insertRoleAccount(map);
    }

    @Override
    public void approvalReject(Map<String, Object> map) {
         roleApplyApprovalDao.approvalReject(map);
    }
}
