package com.zzq.springbootdemo.controller.sys;

import com.github.pagehelper.PageInfo;
import com.zzq.springbootdemo.model.sys.SysPermissionRole;
import com.zzq.springbootdemo.service.sys.SysPermissionRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: TYLER
 * Date: 2019-03-01
 * Time: 17:28
 */
@Controller
@RequestMapping(value = "/sys_permission_role")
public class SysPermissionRoleController {
    @Autowired
    private SysPermissionRoleService sysPermissionRoleService;

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public SysPermissionRole selectByPrimaryKey(@PathVariable(value = "id") int id) {
        //注意@PathVariable(value = "id")是取出url地址中的{id},当然也可以不用写，不过按照规范，最好写上
        return sysPermissionRoleService.selectByPrimaryKey(id);
    }

    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public PageInfo<SysPermissionRole> findAll() {
        return sysPermissionRoleService.findAll(1, 100);
    }

}
