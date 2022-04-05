package com.example.one.handle;

import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        System.out.println("hello AuthenticationEntryPoint");

        Map<String,Object> map = new HashMap<>();
        // 保存数据
        map.put("code","403");
        map.put("msg","hello AuthenticationEntryPoint catch");
        map.put("data","");
        // 设置返回消息类型
        httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        // 返回给请求端
        httpServletResponse.getWriter().write(JSON.toJSON(map).toString());
    }
}
