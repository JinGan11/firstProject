package com.ucar.combination.dao;

import java.util.List;
import java.util.Map;

/**
 * description:角色审批
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-08-14 12:40:26
 **/
public interface RoleApplyApprovalDao {

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
     * @params map
     * @return
     */
    void approvalReject(Map<String, Object> map);

    /**
     * description:角色审批通过，插入角色账户信息
     * @author qingyu.lan@ucarinc.com
     * @date  2019-08-14 12:46:26
     * @params map
     * @return
     */
    void insertRoleAccount(Map<String, Object> map);

    /**
     * description:角色审批通过，移除对应角色账户信息
     * @author qingyu.lan@ucarinc.com
     * @date  2019-08-15 12:46:26
     * @params map
     * @return
     */
    void removeRoleAccount(Map<String, Object> map);

    /**
     * description:通过角色id查找关联账号
     * @author qingyu.lan@ucarinc.com
     * @date  2019-08-15 12:48:26
     * @params id
     * @return
     */
    List<Integer> selectRoleAccountByRoleId(Map<String, Object> map);


}
