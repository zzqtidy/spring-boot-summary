package com.zzq.springbootdemo.configurer;

import com.zzq.springbootdemo.servlet.MyServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public ServletRegistrationBean myServlet(){
        ServletRegistrationBean<MyServlet> myServletServletRegistrationBean = new ServletRegistrationBean<>(new MyServlet(), "/myServlet");
        return myServletServletRegistrationBean;
    }
}
