package com.ucar.combination.config;

import com.ucar.combination.config.interceptors.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * description:
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-30 15:51:21
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor)
//                .addPathPatterns("/**").excludePathPatterns("/login/login.do_");
    }
}
