package com.example.one.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


// 提交账号密码之后验证失败处理器
// host/login登陆页面输入账号密码验证失败以后的处理器
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure( HttpServletRequest request, HttpServletResponse response,
                                         AuthenticationException exception)
            throws IOException, ServletException {
        System.out.println("start custom auth fail");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String,Integer> test = new HashMap<String, Integer>();
        test.put("age",18);
        for(Integer k : test.values()){
            System.out.println("test value = "+k);
        }
        Iterator<Map.Entry<String, Integer>> it = test.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Integer> entry = it.next();
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }

        // 返回一个data
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", Calendar.getInstance().getTime());
        data.put("exception", exception.getMessage());
        response.getOutputStream().println(objectMapper.writeValueAsString(data));
    }
}