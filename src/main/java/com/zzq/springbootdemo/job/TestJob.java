package com.zzq.springbootdemo.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Administrator
 * Date: 2019-05-03
 * Time: 21:42
 */
@Slf4j
public class TestJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        Object paramsObj = jobDataMap.get("params");
        if(paramsObj==null){
            log.error("没有获取到jobDataMap");
        }
        else {
            String params = jobDataMap.get("params").toString();
            log.info(params);
        }

    }
}
