package com.zzq.springbootdemo.security;

import com.zzq.springbootdemo.dao.sys.SysRoleMapper;
import com.zzq.springbootdemo.dao.sys.SysUserRoleMapper;
import com.zzq.springbootdemo.model.sys.SysRole;
import com.zzq.springbootdemo.model.sys.SysUser;
import com.zzq.springbootdemo.model.sys.SysUserRole;
import com.zzq.springbootdemo.service.sys.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:用户验证方法
 * User: TYLER
 * Date: 2019-03-07
 */

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.selectByUserNameAndPermissons(username);
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectByUserId(sysUser.getId());
        List<SysRole> roleList = new ArrayList<>();
        List<String> roles= new ArrayList<>();
        for (SysUserRole sur : sysUserRoles)
        {
            SysRole sysRole = sysRoleMapper.selectByPrimaryKey(sur.getRoleid());
            roles.add(sysRole.getNameEn());
        }
        sysUser.setRoles(roles);
        if (sysUser == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return new JwtUser(sysUser);
        }
    }

}