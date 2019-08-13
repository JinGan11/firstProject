package com.ucar.combination.dao;

import com.ucar.combination.model.AccountHistory;
import org.apache.ibatis.annotations.Param;

/**
 * description:
 *
 * @author zhengyong.liao@ucarinc.com
 * @return
 * @date:
 **/
public interface AccountHistoryDao {
    int insert(@Param("acc") AccountHistory accountHistory);
}
