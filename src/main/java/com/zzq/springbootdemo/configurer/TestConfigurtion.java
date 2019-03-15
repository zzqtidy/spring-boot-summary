package com.zzq.springbootdemo.configurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: TYLER
 * Date: 2019-03-06
 */

@Configuration
//将拦截器添加到SpringBoot的配置中，让SpringBoot项目有这么一个拦截器存在
public class TestConfigurtion extends WebMvcConfigurerAdapter {
    @Autowired
    private TestInterceptor testInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(testInterceptor).addPathPatterns("/**");
    }
}
