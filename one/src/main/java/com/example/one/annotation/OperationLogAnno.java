package com.example.one.annotation;

import com.example.one.po.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author LuoBo
 * @Date 2022/1/26
 * @description  测试切面日志（自定义注解）
 */
@Target({ElementType.METHOD}) //应用于方法上面
@Retention(RetentionPolicy.RUNTIME)//表示在运行时注解任可用
public @interface OperationLogAnno {
    /**
     * 操作位置
     */
    String operatePage() default "";
    /**
     * 操作类型
     */
    String operateType() default "";
    /**
     * 业务域,各自业务自己定义
     */
    String bizType() default "";
    /**
     * 操作类型(枚举类型)
     */
    OperationType operationType() default OperationType.Write;
}