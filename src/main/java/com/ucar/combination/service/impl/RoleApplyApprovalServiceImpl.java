package com.ucar.combination.service.impl;

import com.ucar.combination.dao.RoleApplyApprovalDao;
import com.ucar.combination.service.RoleApplyApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RoleApplyApprovalServiceImpl implements RoleApplyApprovalService {

    @Autowired(required = false)
    private RoleApplyApprovalDao roleApplyApprovalDao;

    @Override
    public void approvalPass(Map<String, Object> map) {
        List<Integer> list = roleApplyApprovalDao.selectRoleAccountByRoleId(map);
        if(map.get("accountIds")!=null){
            String[] ids = (String[])map.get("accountIds");
            List accountIds = new ArrayList();
            for (int i = 0; i <ids.length ; i++) {
                if(!list.contains(Integer.valueOf(ids[i]))){
                    accountIds.add(ids[i]);
                }
            }
            String[] accounts =new String[accountIds.size()];
            for (int i = 0; i <accountIds.size(); i++) {
                accounts[i]= (String) accountIds.get(i);
            }
            map.remove("accountIds");
            if(accounts.length ==0){
                map.put("accountIds","");
            }else{
                map.put("accountIds",accounts);
                roleApplyApprovalDao.insertRoleAccount(map);
            }
        }
        roleApplyApprovalDao.approvalPass(map);
        roleApplyApprovalDao.removeRoleAccount(map);
    }

    @Override
    public void approvalReject(Map<String, Object> map) {
         roleApplyApprovalDao.approvalReject(map);
    }
}
