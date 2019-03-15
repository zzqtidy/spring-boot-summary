package com.zzq.springbootdemo.security;

import com.zzq.springbootdemo.model.sys.SysUser;
import com.zzq.springbootdemo.service.sys.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: TYLER
 * Date: 2019-03-07
 * Time: 21:49
 */
@Controller
@RequestMapping(value = "/auth")
public class JwtAuthController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public String registerUser(@RequestBody Map<String, String> registerUser) {
        SysUser user = new SysUser();
        user.setUsername(registerUser.get("username"));
        // 记得注册的时候把密码加密一下
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.get("password")));
        List<String> roles = new ArrayList<>();
        roles.add("USER_ROLE");
        user.setRoles(roles);
        sysUserService.insert(user);
        return sysUserService.register(user);
    }

    /*
    * 注册是有了，那登录在哪呢？请看UsernamePasswordAuthenticationFilter的源代码
    public UsernamePasswordAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }
    可以看出来默认是/login，所以登录直接使用这个路径就可以啦~当然也可以自定义
    只需要在JWTAuthenticationFilter的构造方法中加入下面那一句话就可以啦
    */
}
