package com.example.one.config;

import com.example.one.interceptor.MyInterceptor;
import com.example.one.po.Car;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration的作用：标注在类上，配置spring容器(应用上下文)。相当于把该类作为spring的xml配置文件中的<beans>
//@Configuration注解的类中，使用@Bean注解标注的方法，返回的类型都会直接注册为bean
@Configuration
public class MyAppConfig implements WebMvcConfigurer {

    // 相当于说配置拦截器的逻辑是
    // 实现WebMvcConfigurer的addInterceptors
    // 其中addInterceptors注入自己的拦截器

    // 这里需要注意的是，过滤器注入的时候才指定过滤那些URL
    // 也就是说，过滤器只是定义了行为
    // 但是具体用在哪些地方是由addInterceptors决定的

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        MyInterceptor myInterceptor = new MyInterceptor();
        // 所有路径都被拦截
        String[] path = {"/**"};
        // 添加不被拦截路径
        String[] excludePath = {"/school/user/lists"};
        registry.addInterceptor(myInterceptor).addPathPatterns(path).excludePathPatterns(excludePath);
    }

    @Bean
    // 产生一个Bean对象，然后这个Bean对象交给Spring管理 - 只会调用一次，Bean对象会放在自己的IOC容器之中
    // 所在的类有注解 @Configuration 才会执行,否则是不会执行的
    public Car car() {
        System.out.println("hello car");
        return new Car();
    }
}