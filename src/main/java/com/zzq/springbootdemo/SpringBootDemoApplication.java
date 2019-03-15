package com.zzq.springbootdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*如果Application类所在的包为：com.demo.module，则只会扫描com.demo.module包及其所有子包，如果service或dao所在包不在com.demo.module及其子包下，则不会被扫描！*/
@MapperScan("com.zzq.springbootdemo.dao")//加上你项目的dao或service所在文件位置即可
@SpringBootApplication
public class SpringBootDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }
}

