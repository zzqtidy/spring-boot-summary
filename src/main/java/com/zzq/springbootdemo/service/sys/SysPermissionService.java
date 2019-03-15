package com.zzq.springbootdemo.service.sys;

import com.github.pagehelper.PageInfo;
import com.zzq.springbootdemo.model.sys.SysPermission;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: TYLER
 * Date: 2019-03-01
 * Time: 21:16
 */
public interface SysPermissionService {
    int deleteByPrimaryKey(int id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

    PageInfo<SysPermission> findAll(int pageNum, int pageSize);
}
