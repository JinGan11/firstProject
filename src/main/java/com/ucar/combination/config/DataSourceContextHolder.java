package com.ucar.combination.config;

import enums.DataSourceTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceContextHolder {

    /** 日志记录器 */
    private static final Logger logger = LoggerFactory.getLogger(DataSourceContextHolder.class);

    private static final ThreadLocal<Integer> local = new ThreadLocal<Integer>();
    public static ThreadLocal<Integer> getLocal() {
        return local;
    }
    /**
     * 读可能是多个库
     */
    public static void read() {
        local.set(DataSourceTypeEnum.READ.getIndex());
        if(logger.isInfoEnabled()){
            logger.info("数据库切换到：读库");
        }
    }
    /**
     * 写只有一个库
     */
    public static void write() {
        local.set(DataSourceTypeEnum.WRITE.getIndex());
        if(logger.isInfoEnabled()){
            logger.info("数据库切换到:写库");
        }

    }
    public static Integer getJdbcType() {
        return local.get();
    }
}
