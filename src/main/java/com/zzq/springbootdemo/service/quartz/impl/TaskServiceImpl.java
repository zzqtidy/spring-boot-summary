package com.zzq.springbootdemo.service.quartz.impl;

import com.zzq.springbootdemo.dao.quartz.JobEntityMapper;
import com.zzq.springbootdemo.model.quartz.JobEntity;
import com.zzq.springbootdemo.service.quartz.ITaskService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Administrator
 * Date: 2019-04-28
 * Time: 23:15
 */
@Slf4j
@Service
public class TaskServiceImpl implements ITaskService {
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private JobEntityMapper jobEntityMapper;

    @Override
    public Boolean addTask(JobEntity jobEntity) {

        return jobEntityMapper.insert(jobEntity)>0;
    }

    @Override
    public Boolean resumeTask(JobEntity jobEntity) {
        return null;
    }

    @Override
    public List<JobEntity> findTaskList(JobEntity jobEntity) {
        return null;
    }

    @Override
    public List<JobEntity> findAllTask() {
        log.info(String.valueOf(scheduler.hashCode()));
        CronTrigger cronTrigger = (CronTrigger) TriggerBuilder.newTrigger();

        return jobEntityMapper.findAllTask();
    }

    @Override
    public Boolean updateTask(JobEntity jobEntity) {
        return null;
    }

    @Override
    public Boolean pauseTask(JobEntity jobEntity) {
        return null;
    }

    @Override
    public Boolean deleteTask(JobEntity jobEntity) {
        return jobEntityMapper.deleteByPrimaryKey(jobEntity.getId())>0;
    }

    @Override
    public boolean checkExists(String jobName, String jobGroup) {
        return false;
    }
}
