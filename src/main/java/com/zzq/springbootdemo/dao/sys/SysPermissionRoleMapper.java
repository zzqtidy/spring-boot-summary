package com.zzq.springbootdemo.dao.sys;

import com.zzq.springbootdemo.model.sys.SysPermissionRole;

import java.util.List;

public interface SysPermissionRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysPermissionRole record);

    int insertSelective(SysPermissionRole record);

    SysPermissionRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysPermissionRole record);

    int updateByPrimaryKey(SysPermissionRole record);

    List<SysPermissionRole> findAll();
}