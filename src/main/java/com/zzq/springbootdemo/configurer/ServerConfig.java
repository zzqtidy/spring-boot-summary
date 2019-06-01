package com.zzq.springbootdemo.configurer;

import com.zzq.springbootdemo.filter.MyFilter;
import com.zzq.springbootdemo.interceptor.MyInterceptor;
import com.zzq.springbootdemo.servlet.MyServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * Description:这个类主要是负责注册三大组件和配置一些内置的servlet容器相关信息
 * User: TYLER
 * Date: 2019-05-22
 * Time: 8:52
 */
@Configuration
public class ServerConfig {
    //将我们定义的servlet加载到容器中
    @Bean
    public ServletRegistrationBean myServlet() {
        String[] urlMappings = {"/myServlet"}; //这里不要配置系统中有用的路径
        ServletRegistrationBean<MyServlet> myServletServletRegistrationBean = new ServletRegistrationBean<>(new MyServlet(), urlMappings);
        return myServletServletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new MyFilter());
        String[] urlPatterns = new String[]{"/sys_user/*"};//配置要拦截的路径
        registrationBean.setUrlPatterns(Arrays.asList(urlPatterns));
        registrationBean.setOrder(2);//设置执行顺序，数字越小越先执行
        return registrationBean;
    }

    @Autowired
    private MyInterceptor myInterceptor;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            //添加拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(myInterceptor).addPathPatterns("/**");//可以配置多个
            }
        };
    }


}
