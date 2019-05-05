package com.zzq.springbootdemo.controller.quartz;

import com.zzq.springbootdemo.dto.JsonResponse;
import com.zzq.springbootdemo.model.quartz.JobEntity;
import com.zzq.springbootdemo.service.quartz.ITaskService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Administrator
 * Date: 2019-04-28
 * Time: 23:24
 */
@Slf4j
@RestController
@RequestMapping(value = "/task")
public class TaskController {
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private ITaskService taskService;

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public List<JobEntity> findAllTasks(){
        return taskService.findAllTask();
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public JsonResponse addJob(@RequestBody JobEntity jobEntity) {

        log.info("添加任务开始……");
        try {

            Class cls = Class.forName(jobEntity.getClassName());
            cls.newInstance();

            //创建Jobdetail
            JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(jobEntity.getName(),
                    jobEntity.getGroup())
                    .withDescription(jobEntity.getDescription()).build();
            //为job添加参数
            jobDetail.getJobDataMap().put("params",jobEntity.getParameter());

            //触发的时间
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobEntity.getCron());
            //触发器和触发时间的绑定
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger"+jobEntity.getName(),jobEntity.getGroup())
                    .startNow()
                    .withSchedule(cronScheduleBuilder)
                    .build();
            //交由Scheduler安排触发
            scheduler.scheduleJob(jobDetail,cronTrigger);
        }
        catch (Exception ex){
            log.info("添加任务异常："+ex.getMessage());
            return new JsonResponse().failure(ex.getMessage());
        }
        log.info("添加任务成功……");
        return new JsonResponse().success();
    }

    @RequestMapping(value = "/trigger",method = RequestMethod.POST)
    public JsonResponse triggerJob(@RequestBody JobEntity jobEntity){
        log.info("开始触发任务……");
        try {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("params",jobEntity.getParameter());
            scheduler.triggerJob(getJobByKey(jobEntity),jobDataMap);
        }
        catch (Exception ex){
            log.info("触发任务异常："+ex.getMessage());
            return new JsonResponse().failure(ex.getMessage());
        }
        finally {

        }
        log.info("触发任务成功……");
        return new JsonResponse().success();
    }

    @RequestMapping(value = "/pause",method = RequestMethod.POST)
    public JsonResponse pauseJob(@RequestBody JobEntity jobEntity){
        log.info("暂停任务开始……");
        try {
            scheduler.pauseJob(getJobByKey(jobEntity));
        }
        catch (Exception ex){
            log.info("暂停任务异常："+ex.getMessage());
            return new JsonResponse().failure(ex.getMessage());
        }
        finally {

        }
        log.info("暂停任务成功……");
        return new JsonResponse().success();
    }
    @RequestMapping(value = "/resume",method = RequestMethod.POST)
    public JsonResponse resumeJob(@RequestBody JobEntity jobEntity){
        log.info("恢复任务开始……");
        try {
            scheduler.resumeJob(getJobByKey(jobEntity));
        }
        catch (Exception ex){
            log.info("恢复任务异常："+ex.getMessage());
            return new JsonResponse().failure(ex.getMessage());
        }
        finally {

        }
        log.info("恢复任务成功……");
        return new JsonResponse().success();
    }
    @RequestMapping(value = "/remove",method = RequestMethod.POST)
    public JsonResponse removeJob(@RequestBody JobEntity jobEntity){
        log.info("移除任务开始……");
        try {
            scheduler.deleteJob(getJobByKey(jobEntity));
        }
        catch (Exception ex){
            log.info("移除任务异常："+ex.getMessage());
            return new JsonResponse().failure(ex.getMessage());
        }
        finally {

        }
        log.info("移除任务成功……");
        return new JsonResponse().success();
    }


    private JobKey getJobByKey(@RequestBody JobEntity jobEntity) throws Exception {
        JobKey jobKey = new JobKey(jobEntity.getName(),jobEntity.getGroup());
        if(jobKey==null){
            throw new Exception("不存在该任务");
        }
        return jobKey;
    }


}
