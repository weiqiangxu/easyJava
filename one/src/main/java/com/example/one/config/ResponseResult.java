package com.example.one.config;

import java.lang.annotation.*;

/**
 * 返回值包装
 *
 * @author xu
 * @version 2.0.0
 * @date 2022/7/7 11:53
 */

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseResult {
}
