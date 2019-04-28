package com.zzq.springbootdemo.service.quartz;

import com.zzq.springbootdemo.model.quartz.JobEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: tyler
 * Date: 2019-04-28
 * Time: 23:10
 */
public interface ITaskService {
    //保存定时任务
    Boolean addTask(JobEntity jobEntity);
    //开始定时任务
    Boolean resumeTask(JobEntity jobEntity);
    // 查询job
    List<JobEntity> findTaskList(JobEntity jobEntity);
    // 查询所有JOB
    List<JobEntity> findAllTask();
    //修改定时任务
    Boolean updateTask(JobEntity jobEntity);
    //停止任务
    Boolean pauseTask(JobEntity jobEntity);
    //删除任务
    Boolean deleteTask(JobEntity jobEntity);
    //验证任务是否存在
    boolean checkExists(String jobName, String jobGroup);
}
