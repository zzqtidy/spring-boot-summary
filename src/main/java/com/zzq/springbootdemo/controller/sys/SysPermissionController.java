package com.zzq.springbootdemo.controller.sys;

import com.github.pagehelper.PageInfo;
import com.zzq.springbootdemo.model.sys.SysPermission;
import com.zzq.springbootdemo.service.sys.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: TYLER
 * Date: 2019-03-01
 * Time: 17:28
 */
@Controller
@RequestMapping(value="/sys_permission")
public class SysPermissionController {
    @Autowired
    private SysPermissionService sysPermissionService;
    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public SysPermission selectByPrimaryKey(@PathVariable(value = "id") int id){
        //注意@PathVariable(value = "id")是取出url地址中的{id},当然也可以不用写，不过按照规范，最好写上
        return sysPermissionService.selectByPrimaryKey(id);
    }
    @ResponseBody
    @RequestMapping(value = "/all",method = RequestMethod.POST)
    public PageInfo<SysPermission> findAll(){
        return sysPermissionService.findAll(1,100);
    }


    //根据用户名查询该用户的权限
    @ResponseBody
    @RequestMapping(value = "/by_userid/{user_id}",method = RequestMethod.POST)

    public List<SysPermission> findByUserId(@PathVariable(value = "user_id") int userid) {

        return sysPermissionService.selectByUserId(userid);
    }

}
