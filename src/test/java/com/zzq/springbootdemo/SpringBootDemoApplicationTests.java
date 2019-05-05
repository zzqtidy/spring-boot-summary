package com.zzq.springbootdemo;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.zzq.springbootdemo.dao.sys.SysPermissionMapper;
import com.zzq.springbootdemo.model.quartz.JobEntity;
import com.zzq.springbootdemo.model.sys.SysPermission;
import com.zzq.springbootdemo.service.sys.SysPermissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDemoApplicationTests {
    private Logger logger = LoggerFactory.getLogger(SpringBootDemoApplicationTests.class);
    
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Test
    public void contextLoads() {
//        List<SysPermission> sysPermissionList = sysPermissionMapper.selectPermissionByRoleNameEn("ROLE_manager");
//        logger.info(new Gson().toJson(sysPermissionList));
        JobEntity jobEntity = new JobEntity();
        jobEntity.setId(1);
        jobEntity.setName("test_job");
        jobEntity.setGroup("test_job");
        jobEntity.setClassName("com.zzq.springbootdemo.job.TestJob");
        jobEntity.setDescription("测试");
        jobEntity.setCron("0/5 * * * * ?");
        logger.info(new Gson().toJson(jobEntity));
    }

    private Map<String,Object> parseFromSysPermission(List<SysPermission> sysPermissionList){
        return new HashMap<>();

    }
}

