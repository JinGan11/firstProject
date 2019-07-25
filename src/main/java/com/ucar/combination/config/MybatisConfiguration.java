package com.ucar.combination.config;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@Import({DataSourceConfiguration.class})
@MapperScan(basePackages = {"com.ucar.combination.dao"})
public class MybatisConfiguration {

    @Value("${spring.datasource.type}")
    private Class<? extends DataSource> dataSourceType;
    @Value("${spring.datasource.readSize}")
    private String dataSourceSize;

    @Bean
    public SqlSessionFactory sqlSessionFactory(ApplicationContext ac) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(roundRobinDataSouceProxy(ac));
        sqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.ucar.combination.model");
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSession(ApplicationContext ac) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory(ac));
    }

    @Bean
    public DataSourceTransactionManager transactionManager(ApplicationContext ac) {
        return new DataSourceTransactionManager(roundRobinDataSouceProxy(ac));
    }

    @Bean
    public AbstractRoutingDataSource roundRobinDataSouceProxy(ApplicationContext ac) {
        int size = Integer.parseInt(dataSourceSize);
        CombinationRoutingDataSource proxy = new CombinationRoutingDataSource(size);
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        //多个读数据库
        DataSource writeDataSource = (DataSource) ac.getBean("writeDataSource");
//        List<DataSource> readDataSources = (List<DataSource>) ac.getBean("readDataSource");
//        if(readDataSources.size()==size){
//            for (int i = 0; i < size; i++) {
//                targetDataSources.put(i, readDataSources.get(i));
//            }
//            proxy.setTargetDataSources(targetDataSources);
//        }
        DataSource readDataSources = (DataSource) ac.getBean("readDataSource");
//        if(readDataSources.size()==size){
//            for (int i = 0; i < size; i++) {
//                targetDataSources.put(i, readDataSources.get(i));
//            }
//            proxy.setTargetDataSources(targetDataSources);
//        }
        targetDataSources.put(0, readDataSources);
        proxy.setTargetDataSources(targetDataSources);
        proxy.setDefaultTargetDataSource(writeDataSource);

        return proxy;
    }
}
