package com.ucar.combination.config;

import enums.DataSourceTypeEnum;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * description:
 *
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-24 16:05:07
 **/
public class CombinationRoutingDataSource extends AbstractRoutingDataSource {
    private final int dataSourceNumber;
    private AtomicInteger count = new AtomicInteger(0);

    public CombinationRoutingDataSource(int dataSourceNumber) {
        this.dataSourceNumber = dataSourceNumber;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        Integer typeKey = DataSourceContextHolder.getJdbcType();
        if (typeKey==null || DataSourceTypeEnum.WRITE.getIndex() == typeKey) {
            return DataSourceTypeEnum.WRITE.getIndex();
        }
        // 读 简单负载均衡
        int number = count.getAndAdd(1);
        int lookupKey = number % dataSourceNumber;
        return new Integer(lookupKey);
    }
}
