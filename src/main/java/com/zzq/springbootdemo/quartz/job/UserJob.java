package com.zzq.springbootdemo.quartz.job;

import com.google.gson.Gson;
import com.zzq.springbootdemo.model.sys.SysUser;
import com.zzq.springbootdemo.service.sys.SysUserService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: TYLER
 * Date: 2019-04-25
 */
public class UserJob extends QuartzJobBean {
    private Logger logger = LoggerFactory.getLogger(UserJob.class);
    @Autowired
    private SysUserService sysUserService;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<SysUser> sysUsers = (List<SysUser>) sysUserService.findAll(10,1);
        logger.info("开始执行调度~~~~");
        logger.info(new Gson().toJson(sysUsers));
    }
}
