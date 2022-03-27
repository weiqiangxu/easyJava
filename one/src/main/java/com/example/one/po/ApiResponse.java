package com.example.one.po;

import lombok.Data;

// 如果配置了lombok的@Data注解，
// @Data 注解在类上,提供类所有属性的 getting 和 setting 方法，此外还提供了equals、canEqual、hashCode、toString 方法
// @Setter：注解在属性上；为属性提供 setting 方法
// @Getter: 注解在属性上；为属性提供 getting 方法
// @Log4j ：注解在类上；为类提供一个 属性名为log 的 log4j 日志对象
// @NoArgsConstructor：注解在类上；为类提供一个无参的构造方法
// @AllArgsConstructor：注解在类上；为类提供一个全参的构造方法
public class ApiResponse {
    public Integer code;
    public String message;
    public Object data;

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
