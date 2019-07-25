package com.ucar.combination.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


/**
 * description:数据源配置
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-24 15:51:50
 **/
@Configuration
public class DataSourceConfiguration {

    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);

    @Value("${spring.datasource.type}")
    private Class<? extends DataSource> dataSourceType;

    /**
     * @param
     * @return javax.sql.DataSource
     * @Desciption: 写库配置
     * @author: qingyu.lan@ucarinc.com
     * @date 2019-07-24
     * @version 1.0
     */
    @Bean(name = "writeDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.write")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    /**
     * @param
     * @return javax.sql.DataSource
     * @Desciption: 读库配置
     * @author: qingyu.lan@ucarinc.com
     * @date 2019-07-24
     * @version 1.0
     */
    @Bean(name = "readDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.read")
    public DataSource readDataSource() {
        if (logger.isInfoEnabled()) {
            logger.info("-------------------- db_read init ---------------------");
        }
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

}
