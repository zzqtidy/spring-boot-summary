package com.zzq.springbootdemo.aspect;

import com.zzq.springbootdemo.annotation.AspectLogOnMethod;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 通用的日志切面
 * 这是一个对注解在方法上某个方法进行AOP
 * User: TYLER
 * Date: 2019-05-24
 */
@Slf4j
@Aspect
@Component
public class AnnotationMethodLogAspect {
    public static long startTime;
    public static long endTime;
    /*
    注意，如果要代理注解，此时就要用@annotation(注解类的全类名)
    * */
    @Pointcut("@annotation(com.zzq.springbootdemo.annotation.AspectLogOnMethod)")
    public void pointCutExpression(){}

    @Before("pointCutExpression()")
    public void before(JoinPoint joinPoint) throws Exception {
        log.info("annotation前置切面……");
        startTime = System.currentTimeMillis();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String requestURI = request.getRequestURI();
        String remoteAddr = request.getRemoteAddr();
        String requestMethod = request.getMethod();

        Signature signature = joinPoint.getSignature();
        String declaringTypeName = signature.getDeclaringTypeName();//请求的类名
        Method method = ((MethodSignature)signature).getMethod();
        String methodName = method.getName();//方法名

        boolean annotationPresent = method.isAnnotationPresent(AspectLogOnMethod.class);
        if(annotationPresent){
            log.info("方法上存在注解");
            AspectLogOnMethod aspectLogOnMethod = method.getAnnotation(AspectLogOnMethod.class);//获取注解
            if(aspectLogOnMethod!=null){
                log.info("AspectLogOnMethod注解上的参数value值是："+aspectLogOnMethod.value());
            }
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
            for (Annotation annotation: declaredAnnotations){
                String annotationClassName = annotation.annotationType().getName();
                log.info("---------------------------------");
                log.info("方法上的注解全类名："+annotationClassName);
                Class c = Class.forName(annotationClassName);
                Annotation annotation1 = method.getAnnotation(c);
                Method[] declaredMethods = annotation1.annotationType().getDeclaredMethods();
                for (Method m:declaredMethods) {
                    Object invoke = m.invoke(annotation1);
                    log.info(m.getName()+"的值是:"+invoke.toString());
                }
            }
        }
        List<Object> args = Arrays.asList(joinPoint.getArgs());//获取方法的参数
        log.info("请求url=" + requestURI + ",客户端ip=" + remoteAddr + ",请求方式=" + requestMethod + ",请求的类名=" + declaringTypeName + ",方法名=" + methodName + ",入参=" + args);
    }
    @After("pointCutExpression()")
    public void after(JoinPoint joinPoint){
        endTime = System.currentTimeMillis() - startTime;
        log.info("annotation后置切面……");
    }
    @AfterReturning(pointcut = "pointCutExpression()", returning = "result")
    public void getAfterReturn(Object result) {
        log.info("本次接口耗时={}ms", endTime);
        log.info("获取执行结束之后的值："+result);
    }
    
}
