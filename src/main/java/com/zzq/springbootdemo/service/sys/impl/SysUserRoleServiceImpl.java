package com.zzq.springbootdemo.service.sys.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzq.springbootdemo.dao.sys.SysUserRoleMapper;
import com.zzq.springbootdemo.model.sys.SysUserRole;
import com.zzq.springbootdemo.service.sys.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: TYLER
 * Date: 2019-03-01
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public int deleteByPrimaryKey(int id) {
        return 0;
    }

    @Override
    public int insert(SysUserRole record) {
        return 0;
    }

    @Override
    public int insertSelective(SysUserRole record) {
        return 0;
    }

    @Override
    public SysUserRole selectByPrimaryKey(int id) {
        return sysUserRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysUserRole record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(SysUserRole record) {
        return 0;
    }

    /*
     * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
     * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
     * pageNum 开始页数
     * pageSize 每页显示的数据条数
     * */
    @Override
    public PageInfo<SysUserRole> findAll(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<SysUserRole> userDomains = sysUserRoleMapper.findAll();
        PageInfo result = new PageInfo(userDomains);
        return result;
    }

    @Override
    public List<SysUserRole> selectByUserId(Integer userid) {
        return sysUserRoleMapper.selectByUserId(userid);
    }
}
