package com.zzq.springbootdemo.dao.sys;

import com.zzq.springbootdemo.model.sys.SysUserRole;

import java.util.List;

public interface SysUserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Integer id);
    List<SysUserRole> selectByUserId(Integer userid);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

    List<SysUserRole> findAll();

}