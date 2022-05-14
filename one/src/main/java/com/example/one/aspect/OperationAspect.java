package com.example.one.aspect;

import cn.hutool.core.date.DateUtil;
import com.example.one.annotation.OperationLogAnno;
import com.example.one.po.OperationLog;
import com.example.one.po.OperationType;
import com.example.one.service.AspectLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Date;

@Slf4j
@Aspect
@Configuration
public class OperationAspect {

    @Autowired
    private AspectLog aspectLog;

    /**
     * 自定义注解作为切点
     */
    @Pointcut("@annotation(com.example.one.annotation.OperationLogAnno)")
    public void operateLog() {
    }

    @Around("operateLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        OperationLog log = new OperationLog();

        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 反射获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        // 拿到方法对应的参数类型
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 根据类、方法、参数类型（重载）获取到方法的具体信息
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);

        // 获取方法上声明的注解
        OperationLogAnno anno = objMethod.getDeclaredAnnotation(OperationLogAnno.class);
        //记录日志时间
        String formatDate = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");

        log.setUsername(methodName);
        Object proceed = joinPoint.proceed();
        // 这里特殊处理原因：安全运营要求es字段尽量复用，操作持续时间字段只能存integer类型
        long duration = System.currentTimeMillis() - startTime;
        //调用接口保存日志
        aspectLog.saveLog(methodName,anno.operateType(),anno.bizType(), OperationType.Write, formatDate);
        return proceed;
    }


}