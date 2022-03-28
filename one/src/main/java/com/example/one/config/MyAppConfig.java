package com.example.one.config;

import com.example.one.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration的作用：标注在类上，配置spring容器(应用上下文)。相当于把该类作为spring的xml配置文件中的<beans>
//@Configuration注解的类中，使用@Bean注解标注的方法，返回的类型都会直接注册为bean
@Configuration
public class MyAppConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        MyInterceptor myInterceptor = new MyInterceptor();
        // 所有路径都被拦截
        String[] path = {"/**"};
        // 添加不被拦截路径
        String[] excludePath = {"/school/user/lists"};
        registry.addInterceptor(myInterceptor).addPathPatterns(path).excludePathPatterns(excludePath);
    }
}