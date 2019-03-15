package com.zzq.springbootdemo.service.sys;

import com.github.pagehelper.PageInfo;
import com.zzq.springbootdemo.model.sys.SysRole;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: TYLER
 * Date: 2019-03-01
 * Time: 21:16
 */
public interface SysRoleService {
    int deleteByPrimaryKey(int id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    PageInfo<SysRole> findAll(int pageNum, int pageSize);
}
