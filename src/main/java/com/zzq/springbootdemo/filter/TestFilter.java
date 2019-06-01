package com.zzq.springbootdemo.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description: 自定义的Filter(@Component，让这个Filter自动加载到Spring容器注册)
 * 在SpringBoot中添加Filter有两种方法，出于统一管理的目的，建议用第2种方式
 * + 1、implements Filter 在类名上添加@Component，让这个Filter自动加载到Spring容器中,
 *      但是不能对该过滤器配置配置需要过滤的 URL，如果我们的过滤器需要过滤全部的链接，用这种方式还是不错的。
 * + 2、implements Filter后不在类名上添加@Component，Filter的加入由我们自定义的ServerConfig类由FilterRegistrationBean注册加入
 *      这种较灵活，推荐使用
 * User: TYLER
 * Date: 2019-03-06
 */
@Slf4j
//@Component
@Order(value = 3)
public class TestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("进入TestFilter Init使用@Component，让这个Filter自动加载到Spring容器注册");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("TestFilter过滤器执行前操作");
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("TestFilter过滤器执行后操作");
    }

    @Override
    public void destroy() {
        log.info("TestFilter过滤器生命周期消亡");
    }
}
