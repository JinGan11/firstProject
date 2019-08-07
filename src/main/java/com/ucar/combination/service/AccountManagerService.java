package com.ucar.combination.service;

import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;

/**
 * description:账户管理
 *
 * @Author junqiang.zhang@ucarinc.com
 * @Version 1.0
 * @Date: 2019/8/1
 **/
public interface AccountManagerService {
    /*
     * description: 账户管理列表
     * @uthor： junqiang.zhang@ucarinc.com
     * @Date：    
     * @PArams： map
     * @Return： 
     */
    ResultPage queryList(QueryParam queryParam);
//lzy
    ResultPage getRoleAccountList(QueryParam queryParam);
}
