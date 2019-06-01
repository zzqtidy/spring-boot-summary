package com.zzq.springbootdemo;

import com.google.gson.Gson;
import com.zzq.springbootdemo.dao.sys.SysPermissionMapper;
import com.zzq.springbootdemo.model.quartz.JobEntity;
import com.zzq.springbootdemo.model.sys.SysPermission;
import com.zzq.springbootdemo.service.sys.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDemoApplicationTests {
    private Logger logger = LoggerFactory.getLogger(SpringBootDemoApplicationTests.class);
    
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private Scheduler scheduler;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Test
    public void contextLoads() {
        List<SysPermission> sysPermissionList = sysPermissionMapper.selectPermissionByRoleNameEn("ROLE_manager");
        logger.info(new Gson().toJson(sysPermissionList));

    }

    @Test
    public void quartTest(){
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

    //以下是RabbitMQ的测试--接收
    @Test
    public void rabbitReceive(){
        String queueName = "com.queue.notify.hello";
        Object o = rabbitTemplate.receiveAndConvert(queueName);
        log.info("获取队列{}中的值为：{}",queueName,o);
    }
    //以下是RabbitMQ的测试--发送
    @Test
    public void rabbitSend(){
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("name", "张三");
        stringStringHashMap.put("age", "23");
        String routingKey = "com.queue.notify.hello";
        rabbitTemplate.convertAndSend(routingKey,stringStringHashMap);
    }

    //发送邮件
    @Autowired
    private JavaMailSender mailSender;
    @Test
    public void sendSimpleMail() throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("960039871@qq.com");
        message.setTo("263720544@qq.com");
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");

        mailSender.send(message);
    }
    @Test
    public void testSendHtml() {
        MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("960039871@qq.com");
            helper.setTo("263720544@qq.com");
            helper.setSubject("标题：发送Html内容");

            StringBuffer sb = new StringBuffer();
            sb.append("<h1>大标题-h1</h1>")
                    .append("<p style='color:#F00'>红色字</p>")
                    .append("<p style='text-align:right'>右对齐</p>");
            helper.setText(sb.toString(), true);
            mailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }


    }
}

