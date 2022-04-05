package com.example.one.handle;

import com.alibaba.fastjson.JSON;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {
        System.out.println("catch access deny handle "+e.getMessage());
        Map<String,String> resultMap = new HashMap<>();
        // 保存数据
        resultMap.put("code","-1");
        resultMap.put("msg","没有权限访问 access deny handle");
        resultMap.put("data",null);

        // 设置返回消息类型
        httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        // 返回给请求端
        httpServletResponse.getWriter().write(JSON.toJSON(resultMap).toString());
    }
}
