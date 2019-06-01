package com.zzq.springbootdemo.service.sys;

import com.github.pagehelper.PageInfo;
import com.zzq.springbootdemo.model.sys.SysUser;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: SysUserYLER
 * Date: 2019-03-01
 * SysUserime: 21:16
 */
public interface SysUserService {
    int deleteByPrimaryKey(int id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    PageInfo<SysUser> findAll(int pageNum, int pageSize);
    SysUser selectByUserNameAndPermissons(String username);

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 操作结果
     */
    String register(SysUser user);

    /**
     * 刷新密钥
     *
     * @param oldToken 原密钥
     * @return 新密钥
     */
    String refreshToken(String oldToken);
}
