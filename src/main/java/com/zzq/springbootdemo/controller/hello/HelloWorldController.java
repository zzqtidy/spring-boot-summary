package com.zzq.springbootdemo.controller.hello;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: TYLER
 * Date: 2019-02-14
 * Time: 17:39
 */
import com.zzq.springbootdemo.annotation.AspectLogOnMethod;
import com.zzq.springbootdemo.service.rabbitmq.IAmqpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController //@RestController是返回json数据的controller，@Controller是返回页面的controller
@RequestMapping("/")
public class HelloWorldController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    IAmqpService amqpService;

    @RequestMapping("/")
    public String HelloWorld() {
        return "helloWorld";
    }

    @Transactional(value = "txManagerJpa",propagation = Propagation.NOT_SUPPORTED)
    @AspectLogOnMethod(value = "/jdbc_test")
    @GetMapping("/jdbc_test")
    public List<Map<String,Object>> getTestByJdbc(){
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("select * from sys_user");
        amqpService.convertAndSend("aaaa");
        return mapList;
    }
}
