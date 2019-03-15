package com.zzq.springbootdemo.dao.sys;

import com.zzq.springbootdemo.model.sys.SysRole;

import javax.management.relation.Role;
import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(int id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> findAll();

    List<SysRole> selectRolesByUserid(Integer userid);

    SysRole selectRolesByNameEn (String name_cn);

}