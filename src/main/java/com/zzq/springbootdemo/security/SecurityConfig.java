package com.zzq.springbootdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: TYLER
 * Date: 2019-03-07
 * Time: 21:33
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    // 因为UserDetailsService的实现类实在太多啦，这里设置一下我们要注入的实现类
    @Qualifier("jwtUserDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    // 加密密码
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //user Details Service验证 用设置加密方式为BCryptPasswordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                //处理跨域请求中的Preflight请求
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                // 测试用资源，需要验证了的用户才能访问
                .antMatchers("/sys_user/**").authenticated()
                // ROLE_USER的权限才能访问资源/user/**
//                .antMatchers("/user/**").hasRole("USER")
                // 任何尚未匹配的URL只需要验证用户即可访问
//                .anyRequest().authenticated()
                // 其他都放行了
                .anyRequest().permitAll().and()
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        http.addFilter(new JwtAuthenticationFilter(authenticationManager()));  //登陆过滤器
        http.addFilter(new JwtAuthorizationFilter(authenticationManager()));//验证成功当然就是进行鉴权
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}
