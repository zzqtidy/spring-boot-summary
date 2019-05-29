package com.zzq.springbootdemo.service.rabbitmq.impl;

import com.zzq.springbootdemo.service.rabbitmq.IAmqpService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author TYLER
 * @title: AmqpServiceImpl
 * @description: TODO
 * @date 2019/5/29
 */
@Service
public class AmqpServiceImpl implements IAmqpService {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void convertAndSend(String message) {
        amqpTemplate.convertAndSend("com.queue.notify.hello", message);
    }
}
