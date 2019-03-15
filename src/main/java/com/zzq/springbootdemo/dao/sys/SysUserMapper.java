package com.zzq.springbootdemo.dao.sys;

import com.zzq.springbootdemo.model.sys.SysUser;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(int id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    List<SysUser> findAll();

    SysUser selectByUserName(String username);
}