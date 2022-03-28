package com.example.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
// 注解Filter\ServletContextListener\HttpServlet - 如果没写这个@WebFilter不生效
@ServletComponentScan
public class OneApplication {

    // @Configuration中所有带@Bean注解的方法都会被动态代理，因此多次调用该方法返回的都是同一个实例。
    // @Component中所有带@Bean注解的方法都不会被动态代理，因此多次调用该方法返回的都是多个实例。
    // 使用@Component,@Service,@Controller,@Repository配置对象到spring容器中
    public static void main(String[] args) {
        SpringApplication.run(OneApplication.class, args);
    }

}
