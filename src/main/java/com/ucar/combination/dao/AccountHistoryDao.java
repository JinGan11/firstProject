package com.ucar.combination.dao;

import com.ucar.combination.model.AccountHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description:
 *
 * @author zhengyong.liao@ucarinc.com
 * @return
 * @date:
 **/
public interface AccountHistoryDao {
    int insert(@Param("acc") AccountHistory accountHistory);
    List<AccountHistory> selectAccountHistoryByAccountId(String accountId);
}

