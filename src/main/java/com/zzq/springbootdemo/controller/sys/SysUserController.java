package com.zzq.springbootdemo.controller.sys;

import com.github.pagehelper.PageInfo;
import com.zzq.springbootdemo.model.sys.SysUser;
import com.zzq.springbootdemo.service.sys.SysBaseService;
import com.zzq.springbootdemo.service.sys.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: TYLER
 * Date: 2019-03-01
 * Time: 17:28
 */
@Controller
@RequestMapping(value = "/sys_user")
public class SysUserController {
    private Logger logger = LoggerFactory.getLogger(SysUserController.class);
    @Autowired
    private SysUserService sysUserService;

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    public SysUser selectByPrimaryKey(@PathVariable(value = "id") int id) {
        //注意@PathVariable(value = "id")是取出url地址中的{id},当然也可以不用写，不过按照规范，最好写上
        return sysUserService.selectByPrimaryKey(id);
    }

    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public PageInfo<SysUser> findAll() {
        return sysUserService.findAll(1, 100);
    }
}
