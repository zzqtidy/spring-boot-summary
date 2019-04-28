package com.zzq.springbootdemo.controller.sys;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.zzq.springbootdemo.dto.JsonResponse;
import com.zzq.springbootdemo.model.sys.SysUser;
import com.zzq.springbootdemo.quartz.job.MyJob;
import com.zzq.springbootdemo.service.sys.SysUserService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;


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

    @Autowired
    private Scheduler scheduler;

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

        //设置开始时间为1分钟后
        long startAtTime = System.currentTimeMillis() + 1000 * 60;
        //任务名称
        String _name = UUID.randomUUID().toString();
        //任务所属分组
        String group = MyJob.class.getName();
        //创建任务
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity(_name,group).build();
        //创建任务触发器
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(_name,group).startAt(new Date(startAtTime)).build();
        //将触发器与任务绑定到调度器内
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return sysUser;
    }

    //注意，如果前台传过来的是一个jsonObject，Controller里面的参数必须写成@RequestBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public JsonResponse updateByPrimaryKeySelective(@RequestBody SysUser sysUser){
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
