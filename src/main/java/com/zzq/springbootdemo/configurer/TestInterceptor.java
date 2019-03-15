package com.zzq.springbootdemo.configurer;

import com.zzq.springbootdemo.model.sys.SysUser;
import com.zzq.springbootdemo.service.sys.SysBaseService;
import com.zzq.springbootdemo.service.sys.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: TYLER
 * Date: 2019-03-06
 */
@Component
public class TestInterceptor implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(TestInterceptor.class);

    @Autowired
    private SysUserService sysUserService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.info("=====================>开始进入请求地址拦截,拦截器TestInterceptor：preHandle");
        SysUser sysUser= sysUserService.selectByPrimaryKey(3);

        //如果返回false，那么将不会继续往后执行
        return sysUser!=null;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.info("=====================>处理请求完成后视图渲染之前的处理操作,拦截器TestInterceptor：postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.info("=====================>视图渲染之后的操作,拦截器TestInterceptor：afterCompletion");
    }

}
