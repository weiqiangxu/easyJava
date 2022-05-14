package com.example.one.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

// @WebFilter 用于将一个类声明为过滤器，该注解将会在部署时被容器处理，
// 容器将根据具体的属性配置将相应的类部署为过滤器。该注解具有下表给出的一些常用属性
// ( 以下所有属性均为可选属性，但是 value、urlPatterns、servletNames 三者必需至少包含一个，
// 且 value 和 urlPatterns 不能共存，如果同时指定，通常忽略 value 的取值 )


// 这里传入的参数：
// 指定了过滤器的name属性
// 指定了过滤器的URL匹配模式
@WebFilter(urlPatterns = "/*", filterName = "MyFilter ")
public class CharacterEncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // System.out.println("CharacterEncodingFilter初始化");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.setContentType("text/html;charset=UTF-8");
        // System.out.println("CharacterEncodingFilter执行前....");
        filterChain.doFilter(servletRequest,servletResponse); //让我们的请求继续走，如果不写，程序到这里就被拦截停止
        // System.out.println("CharacterEncodingFilter执行后....");
    }

    @Override
    public void destroy() {
        // System.out.println("CharacterEncodingFilter销毁");
        Filter.super.destroy();
    }
}
