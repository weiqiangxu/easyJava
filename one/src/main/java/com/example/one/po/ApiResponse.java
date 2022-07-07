package com.example.one.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

// 如果配置了lombok的@Data注解，
// @Data 注解在类上,提供类所有属性的 getting 和 setting 方法，此外还提供了equals、canEqual、hashCode、toString 方法
// @Setter：注解在属性上；为属性提供 setting 方法
// @Getter: 注解在属性上；为属性提供 getting 方法
// @Log4j ：注解在类上；为类提供一个 属性名为log 的 log4j 日志对象
// @NoArgsConstructor：注解在类上；为类提供一个无参的构造方法
// @AllArgsConstructor：注解在类上；为类提供一个全参的构造方法
@Data
public class ApiResponse<T> {

    public static final int CODE_OK = 0;

    public static final int CODE_ERR_COMMON = -1;

    public static final int CODE_PARAMETER_VALIDATE_FAILED = 1;

    public static final int CODE_NEED_AUTH = 401;

    public static final int CODE_ACCESS_DENIED = 403;

    public static final int CODE_REQUEST_TIMEOUT = 408;

    public static final String MSG_SUCCEED = "succeed";
    private static final ObjectMapper OM = new ObjectMapper();
    private int code;
    private String message;

    private static final ApiResponse<Object> OK = new ApiResponse<>(CODE_OK, MSG_SUCCEED);

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ApiResponse<Object> ok() {
        return OK;
    }

    public static ApiResponse<Object> newInstance(int code, String message) {
        return new ApiResponse<>(code, message);
    }

    public static <E> ApiResponse<E> newInstance(int code, String message, E data) {
        return new ApiResponse<>(code, message, data);
    }

    public static <E> ApiResponse<E> newInstance(E data) {
        try {
            return new ApiResponse<>(CODE_OK, "response.success", data);
        } catch (Exception e) {
            return new ApiResponse<>(CODE_OK, MSG_SUCCEED, data);
        }
    }

    public String toJson() {
        try {
            return OM.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

}
