package com.zzq.springbootdemo.controller.quartz;

import com.zzq.springbootdemo.model.quartz.JobEntity;
import com.zzq.springbootdemo.service.quartz.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping(value = "/task")
public class TaskController {
    @Autowired
    private ITaskService taskService;

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public List<JobEntity> findAllTasks(){
        return taskService.findAllTask();
    }


}
