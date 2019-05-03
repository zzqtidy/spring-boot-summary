package com.zzq.springbootdemo.dao.quartz;

import com.zzq.springbootdemo.model.quartz.JobEntity;

import java.util.List;

public interface JobEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JobEntity record);

    int insertSelective(JobEntity record);

    JobEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JobEntity record);

    int updateByPrimaryKey(JobEntity record);

    List<JobEntity> findAllTask();
}