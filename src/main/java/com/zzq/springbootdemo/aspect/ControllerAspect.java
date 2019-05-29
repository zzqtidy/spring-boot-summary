package com.zzq.springbootdemo.aspect;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 通用的日志切面
 * AOP的实现原理实际上是动态代理，可以具体了解下
 * + 首先，要加入到IOC容器中，成为一个SpringBean
 * + 然后添加Aspect注解
 * User: TYLER
 * Date: 2019-05-24
 */
@Slf4j
@Aspect
//@Component
public class ControllerAspect {
    public static long startTime;
    public static long endTime;
    /*@Pointcut注解表示表示横切点，哪些方法需要被横切,这样单独提出来为了不在其他地方再写一次execution(public * com.zzq.springbootdemo.controller.*.*(..))*/
    /*切点表达式,以下表示为controller下面的所有方法添加日志*/
    @Pointcut("execution(public * com.zzq.springbootdemo.controller.*.*.*(..))")
    public void pointCutExpression(){}

    @Before("pointCutExpression()")
    public void before(JoinPoint joinPoint){
        log.info("前置切面……");
        startTime = System.currentTimeMillis();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String requestURI = request.getRequestURI();
        String remoteAddr = request.getRemoteAddr();   //这个方法取客户端ip"不够好"
        String requestMethod = request.getMethod();
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        log.info("请求url=" + requestURI + ",客户端ip=" + remoteAddr + ",请求方式=" + requestMethod + ",请求的类名=" + declaringTypeName + ",方法名=" + methodName + ",入参=" + args);
    }
    @After("pointCutExpression()")
    public void after(JoinPoint joinPoint){
        endTime = System.currentTimeMillis() - startTime;
        log.info("后置切面……");
    }
    @AfterReturning(pointcut = "pointCutExpression()", returning = "result")
    public void getAfterReturn(Object result) {
        log.info("本次接口耗时={}ms", endTime);
        log.info("获取执行结束之后的值："+result.toString());
    }

}
