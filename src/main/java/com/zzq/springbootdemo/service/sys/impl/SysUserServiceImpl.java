package com.zzq.springbootdemo.service.sys.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzq.springbootdemo.dao.sys.SysPermissionMapper;
import com.zzq.springbootdemo.dao.sys.SysUserMapper;
import com.zzq.springbootdemo.model.sys.SysPermission;
import com.zzq.springbootdemo.model.sys.SysUser;
import com.zzq.springbootdemo.service.sys.SysUserService;
import com.zzq.springbootdemo.util.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: TYLER
 * Date: 2019-03-01
 * Time: 17:17
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public int deleteByPrimaryKey(int id) {
        return 0;
    }

    @Override
    public int insert(SysUser record) {
        return 0;
    }

    @Override
    public int insertSelective(SysUser record) {
        return 0;
    }

    @Override
    public SysUser selectByPrimaryKey(int id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysUser record) {
        return sysUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKey(SysUser record) {
        return sysUserMapper.updateByPrimaryKey(record);
    }

    /*
     * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
     * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
     * pageNum 开始页数
     * pageSize 每页显示的数据条数
     * */
    @Override
    public PageInfo<SysUser> findAll(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> userDomains = sysUserMapper.findAll();
        PageInfo result = new PageInfo(userDomains);
        return result;
    }

    @Override
    public SysUser selectByUserNameAndPermissons(String username) {
        SysUser sysUser =  sysUserMapper.selectByUserName(username);
        //获取用户对应的权限
        List<SysPermission> sysPermissionList = sysPermissionMapper.selectPermissionByUserId(sysUser.getId());
        sysUser.setSysPermissionList(sysPermissionList);

        return sysUser;
    }


    @Override
    public String register(SysUser sysUser) {
        String username = sysUser.getUsername();
        if (sysUserMapper.selectByUserName(username) != null) {
            return "用户已存在";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = sysUser.getPassword();
        sysUser.setPassword(encoder.encode(rawPassword));
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        //sysUser.setRoles(roles);
        sysUserMapper.insert(sysUser);

        return "success";
    }

    @Override
    public String refreshToken(String oldToken) {
        String token = oldToken.substring("Bearer ".length());
        if (!JwtUtil.isTokenExpired(token)) {
            return JwtUtil.refreshToken(token);
        }
        return "error";
    }
}
