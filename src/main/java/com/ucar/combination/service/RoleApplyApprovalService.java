package com.ucar.combination.service;

import java.util.Map;

/**
 * description:角色审批
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-08-14 12:40:26
 **/
public interface RoleApplyApprovalService {

    /**
     * description:角色审批通过
     * @author qingyu.lan@ucarinc.com
     * @date  2019-08-14 12:43:26
     * @params map
     * @return
     */
    void approvalPass(Map<String, Object> map);

    /**
     * description:角色审批拒绝
     * @author qingyu.lan@ucarinc.com
     * @date  2019-08-14 12:44:26
     * @params id
     * @return
     */
    void approvalReject(Map<String, Object> map);
}
