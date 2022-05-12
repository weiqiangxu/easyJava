package com.example.one.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE}) //声明应用在属性上
@Retention(RetentionPolicy.RUNTIME) //运行期生效
@Documented
public @interface Registry {
    String value() default "";
}