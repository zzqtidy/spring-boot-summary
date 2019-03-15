package com.zzq.springbootdemo.service.sys;

import com.github.pagehelper.PageInfo;
import com.zzq.springbootdemo.model.sys.SysPermissionRole;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: TYLER
 * Date: 2019-03-01
 * Time: 21:16
 */
public interface SysPermissionRoleService {
    int deleteByPrimaryKey(int id);

    int insert(SysPermissionRole record);

    int insertSelective(SysPermissionRole record);

    SysPermissionRole selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(SysPermissionRole record);

    int updateByPrimaryKey(SysPermissionRole record);

    PageInfo<SysPermissionRole> findAll(int pageNum, int pageSize);
}
