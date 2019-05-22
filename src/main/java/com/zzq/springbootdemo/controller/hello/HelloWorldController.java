package com.zzq.springbootdemo.controller.hello;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: TYLER
 * Date: 2019-02-14
 * Time: 17:39
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @RequestMapping("/")
    public String HelloWorld() {
        return "helloWorld";
    }

    @GetMapping("/jdbc_test")
    public List<Map<String,Object>> getTestByJdbc(){
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("select * from sys_user");
        return mapList;
    }
}
