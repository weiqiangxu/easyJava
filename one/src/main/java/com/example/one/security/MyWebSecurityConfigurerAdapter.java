package com.example.one.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity // 开启WebSecurity模式
public class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    // 1、security开启的方式是导入security然后实现WebSecurityConfigurerAdapter

    // 下面三个类是核心

    // WebSecurityConfigurerAdapter： 自定义Security策略
    // AuthenticationManagerBuilder：自定义认证策略
    // @EnableWebSecurity：开启WebSecurity模式


    // 定制请求的授权规则
    // 可以指定我想给哪些路由的访问增加限制
    // 我想给哪些路由的访问开放
    @Override
    protected void configure(HttpSecurity http) throws Exception {

    }


    // 定义认证规则
    // 这个定义了认证规则，比如说我需要用户名root加上密码123456就可以通过认证
    // 可以auth.inMemoryAuthentication().withUser("xxx").password("xxx").roles("admin")
    // 以上面的形式直接往内存中定义用户名和密码(使用该用户名和密码可以通过认证)
    //
    // AuthenticationManagerBuilder.Provider
    // 以上是我们团队的auth模块的实现方式：提供Provider注入一个Interface
    // 在微服务order\pay\等模块自己实现自己的认证规则
    // 大概有:token实现\session实现
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    }
}