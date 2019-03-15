package com.zzq.springbootdemo.dao.sys;

import com.zzq.springbootdemo.model.sys.SysPermission;

import java.util.List;

public interface SysPermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

    List<SysPermission> findAll();

    List<SysPermission> selectPermissionByRoleNameEn(String name_en);

    List<SysPermission> selectPermissionByUserId(Integer userid);
}