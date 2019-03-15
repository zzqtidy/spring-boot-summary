package com.zzq.springbootdemo.controller.hello;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: TYLER
 * Date: 2019-02-14
 * Time: 17:39
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //@RestController是返回json数据的controller，@Controller是返回页面的controller
@RequestMapping("/")
public class HelloWorldController {

    @RequestMapping("/")
    public String HelloWorld() {
        return "helloWorld";
    }
}
