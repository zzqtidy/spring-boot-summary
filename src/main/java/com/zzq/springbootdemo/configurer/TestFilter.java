package com.zzq.springbootdemo.configurer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: TYLER
 * Date: 2019-03-06
 */
@Component
public class TestFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(TestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("进入TestFilter Init===========================");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("过滤器执行前》》》》》》");
        filterChain.doFilter(servletRequest, servletResponse);
        logger.info("过滤器执行后》》》》》》");
    }

    @Override
    public void destroy() {

    }
}
