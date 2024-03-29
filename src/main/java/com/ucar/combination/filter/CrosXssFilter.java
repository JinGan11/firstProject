package com.ucar.combination.filter;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @version 1.0
 * @description: 允许跨域
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-08-03 08:54
 */
@WebFilter(urlPatterns = "/*")
public class CrosXssFilter implements Filter{
    // 多个跨域域名设置
    public static final String[] ALLOW_DOMAIN = {"http://localhost:8081","http://10.104.118.111:8082",
            "http://10.104.118.111:8080","http://10.104.118.111:8081","http://10.104.118.111:80",
            "http://10.104.118.111", "http://localhost:8082","http://localhost:8083", "http://localhost:8084"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //跨域设置
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String originHeader = req.getHeader("Origin");
        if (Arrays.asList(ALLOW_DOMAIN).contains(originHeader)) {
            //通过在响应 header 中设置 ‘*’ 来允许来自所有域的跨域请求访问。
            res.setHeader("Access-Control-Allow-Origin", originHeader);
            //通过对 Credentials 参数的设置，就可以保持跨域 Ajax 时的 Cookie
            //设置了Allow-Credentials，Allow-Origin就不能为*,需要指明具体的url域
            res.setHeader("Access-Control-Allow-Credentials", "true");
            //请求方式
            res.setHeader("Access-Control-Allow-Methods", "*");
            //（预检请求）的返回结果（即 Access-Control-Allow-Methods 和Access-Control-Allow-Headers 提供的信息） 可以被缓存多久
            res.setHeader("Access-Control-Max-Age", "86400");
            //首部字段用于预检请求的响应。其指明了实际请求中允许携带的首部字段
            //res.setHeader("Access-Control-Allow-Headers", "*");
            res.setHeader("Access-Control-Allow-Headers", "Timestamp,Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token,Access-Control-Allow-Headers");
        }
//        XssHttpServletRequestWrapper xssHttpServletRequestWrapper = new XssHttpServletRequestWrapper(
//                httpServletRequest);
//        chain.doFilter(xssHttpServletRequestWrapper, response);
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
