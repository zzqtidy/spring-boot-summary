package com.zzq.springbootdemo.util.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zzq.springbootdemo.model.sys.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:安全用户模型
 * User: TYLER
 * Date: 2019-03-07
 */
public class JwtUser implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    // 写一个能直接使用sys_user创建jwtUser的构造器
    public JwtUser(SysUser sysUser) {
        id = sysUser.getId();
        username = sysUser.getUsername();
        password = sysUser.getPassword();
        //转化单一的角色
        //authorities =Collections.singleton(new SimpleGrantedAuthority(sysUser.getRoles().get(0)));
        //转化多个角色
        authorities = sysUser.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

}