package com.zzq.springbootdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@EnableTransactionManagement // 开启注解事务管理，等同于xml配置文件中的 <tx:annotation-driven />
/*如果Application类所在的包为：com.demo.module，则只会扫描com.demo.module包及其所有子包，如果service或dao所在包不在com.demo.module及其子包下，则不会被扫描！*/
@MapperScan("com.zzq.springbootdemo.dao")//加上你项目的dao或service所在文件位置即可
@SpringBootApplication
public class SpringBootDemoApplication implements TransactionManagementConfigurer {

    @Resource(name = "txManagerDs")
    private PlatformTransactionManager txManager1;
    @Resource(name = "txManagerJpa")
    private PlatformTransactionManager txManager2;
    // 创建事务管理器1
    @Bean(name = "txManagerDs")
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    // 创建事务管理器2
    @Bean(name = "txManagerJpa")
    public PlatformTransactionManager txManager2(EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
    //实现接口 TransactionManagementConfigurer 方法，其返回值代表在拥有多个事务管理器的情况下默认使用的事务管理器
    //如果不自定义，那么springboot默认是采用的JpaTransactionManager管理事务
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {

        System.out.println(">>>>>>>>>>该框架默认采用的事务实现类是：JpaTransactionManager");
        System.out.println(">>>>>>>>>>该框架自定义采用的事务实现类是：" + txManager1.getClass().getName());
        return txManager1;

    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

}

