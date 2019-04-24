package com.zzq.springbootdemo.controller.sys;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.zzq.springbootdemo.dto.JsonResponse;
import com.zzq.springbootdemo.model.sys.SysUser;
import com.zzq.springbootdemo.service.sys.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: TYLER
 * Date: 2019-03-01
 * Time: 17:28
 */
//@Controller
@RestController
@RequestMapping(value = "/sys_user")
public class SysUserController {
    private Logger logger = LoggerFactory.getLogger(SysUserController.class);
    @Autowired
    private SysUserService sysUserService;

    //    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public SysUser selectByPrimaryKey(@PathVariable(value = "id") int id) {
        //注意@PathVariable(value = "id")是取出url地址中的{id},当然也可以不用写，不过按照规范，最好写上
        return sysUserService.selectByPrimaryKey(id);
    }

    //    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public PageInfo<SysUser> findAll() {
        return sysUserService.findAll(1, 100);
    }

    @RequestMapping(value = "/by_username/{user_name}", method = RequestMethod.POST)
    public SysUser findByUserName(@PathVariable(value = "user_name") String name) {
        SysUser sysUser = sysUserService.selectByUserNameAndPermissons(name);
        return sysUser;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public JsonResponse updateByPrimaryKeySelective(@PathVariable(value = "sys_user") String sysUserStr){
        SysUser sysUser = new Gson().fromJson(sysUserStr,SysUser.class);
        int count = sysUserService.updateByPrimaryKeySelective(sysUser);
        if (count>0){
            return new JsonResponse().success();
        }
        else
        {
            return new JsonResponse().failure();
        }
    }
}
