package com.example.one.config;

import com.example.one.config.ResponseResult;
import com.example.one.po.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * todo {这里必须加注释}
 *
 * @author xuweiqiang
 * @version 2.0.0
 * @date 2022/7/7 11:52
 */

@Slf4j
@RestControllerAdvice(annotations = ResponseResult.class)
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> type = returnType.getParameterType();
        log.debug("return type is :{}", type);
        return !ApiResponse.class.isAssignableFrom(type) && !ModelAndView.class.isAssignableFrom(type)
                && !CharSequence.class.isAssignableFrom(type) && !ResponseEntity.class.isAssignableFrom(type);
    }

    @Override
    public Object beforeBodyWrite(
            Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        log.debug("going to convert response body to ApiResponse");
        return ApiResponse.newInstance(body);
    }
}
