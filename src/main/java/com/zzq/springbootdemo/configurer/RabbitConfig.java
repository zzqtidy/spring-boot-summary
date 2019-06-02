package com.zzq.springbootdemo.configurer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author TYLER
 * @title: RabbitmqConfig
 * @description: 配置 RabbitMQ 并以 Bean 的方式显示注入
 * RabbitMQ 在发送接收处理消息时相关 Bean 组件配置其中典型的配置是 RabbitTemplate 以及 SimpleRabbitListenerContainerFactory，
 * 前者是充当消息的发送组件，后者是用于管理  RabbitMQ监听器listener 的容器工厂
 *
 * 注意，要获取配置文件中字段值，有两种方式
 *  + @ConfigurationProperties 自动绑定，这种适合某个class中字段比较多的情况，
 *    注意，这种需要class必须是一个javabean，也就是要有getter和setter
 *    如果要指定加载的其他配置，还需要配合@PropertySource(value = {"classpath:xxx.properties"})加载指定的配置文件
 *
 *  + @如果字段比较少，只是简单地获取使用下，可以用@value(${xxx.xxx.xxx})放在特定的字段上，如果要指定加载的其他配置，
 *    还需要配合@PropertySource(value = {"classpath:xxx.properties"})加载指定的配置文件
 * @date 2019/5/29
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
//@PropertySource(value = {"classpath:application-dev.properties"})
public class RabbitConfig {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String virtualHost;
    private boolean publisherConfirms;

    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public Integer getPort() {
        return port;
    }
    public void setPort(Integer port) {
        this.port = port;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getVirtualHost() {
        return virtualHost;
    }
    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }
    public boolean isPublisherConfirms() {
        return publisherConfirms;
    }
    public void setPublisherConfirms(boolean publisherConfirms) {
        this.publisherConfirms = publisherConfirms;
    }
    public RabbitConfig() {
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(this.host);
        connectionFactory.setPort(this.port);
        connectionFactory.setUsername(this.username);
        connectionFactory.setPassword(this.password);
        connectionFactory.setVirtualHost(this.virtualHost);
        connectionFactory.setPublisherConfirms(this.publisherConfirms);
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(this.connectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

}
