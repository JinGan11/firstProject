package com.ucar.combination.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAop {

    /** 日志记录器 */
    private static final Logger logger = LoggerFactory.getLogger(DataSourceAop.class);

    @Pointcut("@annotation(com.ucar.combination.config.annotation.WriteDataSource)")
    public void writeMethod(){}

    @Pointcut("@annotation(com.ucar.combination.config.annotation.ReadDataSource)")
    public void readMethod(){}

    @Before("writeMethod()")
    public void beforeWrite(JoinPoint point) {
        DataSourceContextHolder.write();
        if(logger.isInfoEnabled()){
            logger.info("dataSource切换到：write");
        }
    }
    @Before("readMethod()")
    public void beforeRead(JoinPoint point) throws ClassNotFoundException {
        //设置数据库为读数据
        DataSourceContextHolder.read();
        if(logger.isInfoEnabled()){
            logger.info("dataSource切换到：Read");
        }
    }
}
