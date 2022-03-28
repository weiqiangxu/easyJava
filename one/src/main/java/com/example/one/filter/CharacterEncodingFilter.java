package com.example.one.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", filterName = "MyFilter ")
public class CharacterEncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("CharacterEncodingFilter初始化");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.setContentType("text/html;charset=UTF-8");
        System.out.println("CharacterEncodingFilter执行前....");
        filterChain.doFilter(servletRequest,servletResponse); //让我们的请求继续走，如果不写，程序到这里就被拦截停止
        System.out.println("CharacterEncodingFilter执行后....");
    }

    @Override
    public void destroy() {
        System.out.println("CharacterEncodingFilter销毁");
        Filter.super.destroy();
    }
}
