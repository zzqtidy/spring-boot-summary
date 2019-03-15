package com.zzq.springbootdemo.service.sys;

import com.github.pagehelper.PageInfo;
import com.zzq.springbootdemo.model.sys.SysUserRole;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: SysUserRoleYLER
 * Date: 2019-03-01
 * SysUserRoleime: 21:16
 */
public interface SysUserRoleService {
    int deleteByPrimaryKey(int id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

    PageInfo<SysUserRole> findAll(int pageNum, int pageSize);
    List<SysUserRole> selectByUserId(Integer userid);
}
