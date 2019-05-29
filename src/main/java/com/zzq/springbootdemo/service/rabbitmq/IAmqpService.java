package com.zzq.springbootdemo.service.rabbitmq;

/**
 * @author TYLER
 * @title: IAmqpService
 * @description: TODO
 * @date 2019/5/29
 */
public interface IAmqpService {
    public void convertAndSend(String message);
}
