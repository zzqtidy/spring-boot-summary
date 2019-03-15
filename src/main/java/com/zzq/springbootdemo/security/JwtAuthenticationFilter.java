package com.zzq.springbootdemo.security;

import com.google.gson.Gson;
import com.zzq.springbootdemo.dao.sys.SysPermissionMapper;
import com.zzq.springbootdemo.dto.JsonResult;
import com.zzq.springbootdemo.model.sys.SysPermission;
import com.zzq.springbootdemo.model.sys.SysUser;
import com.zzq.springbootdemo.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * JWTAuthenticationFilter继承于UsernamePasswordAuthenticationFilter
 * 该拦截器用于获取用户登录的信息，只需创建一个token并调用authenticationManager.authenticate()让spring-security去进行验证就可以了，
 * 不用自己查数据库再对比密码了，这一步交给spring去操作。
 * 这个操作有点像是shiro的subject.login(new UsernamePasswordToken())，验证的事情交给框架。
 * User: TYLER
 * Date: 2019-03-07
 * Time: 21:13
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 从输入流中获取到登录的信息
        try {
            //从json中获取username和password
            String req_body = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
            logger.debug("========================>" + req_body);
            String username = null, password = null;
            if (StringUtils.hasText(req_body)) {
                SysUser sysUser = new Gson().fromJson(req_body, SysUser.class);
                username = sysUser.getUsername();
                password = sysUser.getPassword();
            }
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            return authenticate;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("授权失败：" + e.getMessage());
            return null;
        }
    }

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    // 成功验证后调用的方法 // 如果验证成功，就生成token并返回
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        JsonResult<Object> jsonResult = new JsonResult<>();
        Gson gson = new Gson();
        try {
            // 查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
            // 所以就是JwtUser啦
            JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
            logger.info(gson.toJson(jwtUser));


            //如果想在程序中获得当前登陆用户对应的对象。
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getPrincipal();
//        logger.info(gson.toJson(userDetails));

            //如果想获得当前登陆用户所拥有的所有角色（权限，如果认证时添加了权限就有）。
            Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
            logger.info("当前用户角色："+gson.toJson(authorities));

            String aa = request.getScheme()+":"+request.getHeader("host");
            logger.info(aa);
            logger.info(request.getLocalName());


            //登录验证成功之后将用户的角色和权限写回到客户端（当然，这一步可以通过调用其他方法二次获取）
            response.setCharacterEncoding("utf-8");//设置Response的编码方式为UTF-8
            //向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8,其实设置了本句，也默认设置了Response的编码方式为UTF-8，但是开发中最好两句结合起来使用 ,设置响应头，控制浏览器以指定的字符编码编码进行显示，
            response.setContentType("application/json;charset=UTF-8");

            // 返回创建成功的token
            // 但是这里创建的token只是单纯的token
            // 按照jwt的规定，最后请求的格式应该是 `Bearer token`


            String token = JwtUtil.getToken(jwtUser.getUsername());
            response.setHeader("token", JwtUtil.TOKEN_PREFIX + token);
            jsonResult.success(null);
        } catch (Exception ex) {
            logger.error("登陆验证失败," + ex.getLocalizedMessage());
            jsonResult.failure("登陆验证失败," + ex.getMessage());
        } finally {
            PrintWriter writer = response.getWriter();
            writer.write(gson.toJson(jsonResult));
        }

    }

    // 这是验证失败时候调用的方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().write("authentication failed, reason: " + failed.getMessage());
    }
}

