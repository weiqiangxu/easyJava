package com.example.one.security;

import com.example.one.handle.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.util.Arrays;

@EnableWebSecurity // 开启WebSecurity模式
public class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    // 1、security开启的方式是导入security然后实现WebSecurityConfigurerAdapter

    // 下面三个类是核心

    // WebSecurityConfigurerAdapter： 自定义Security策略
    // AuthenticationManagerBuilder：自定义认证策略
    // @EnableWebSecurity：开启WebSecurity模式


    // 异常分类:认证异常和权限异常
    // https://www.cnblogs.com/wangstudyblog/p/14823512.html


    // 注入无权限异常时候的处理器
    // 基于字段注入
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;


    // 基于set方法注入
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    @Autowired
    public void setMyAuthenticationEntryPoint(MyAuthenticationEntryPoint myAuthenticationEntryPoint) {
        this.myAuthenticationEntryPoint = myAuthenticationEntryPoint;
    }

    private MyAuthenticationProvider myAuthenticationProvider;
    @Autowired
    public void setMyAuthenticationProvider(MyAuthenticationProvider myAuthenticationProvider){
        this.myAuthenticationProvider = myAuthenticationProvider;
    }

    private UserSmsAuthenticationProvider userSmsAuthenticationProvider;
    @Autowired
    public void setUserSmsAuthenticationProvider(UserSmsAuthenticationProvider userSmsAuthenticationProvider){
        this.userSmsAuthenticationProvider = userSmsAuthenticationProvider;
    }



    // 定制请求的授权规则
    // 可以指定我想给哪些路由的访问增加限制
    // 我想给哪些路由的访问开放
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 允许所有链接可以访问
        http.authorizeRequests().antMatchers("/").permitAll();
        // 限制部分链接访问
        http.authorizeRequests().antMatchers("/security/get").hasRole("admin");
        // 角色test才可以访问
        http.authorizeRequests().antMatchers("/security/test").hasAuthority("test");

        // 验证所有链接
        // http.authorizeRequests().anyRequest().authenticated();

        // form表单登陆失败handle
        http.formLogin().failureHandler(customAuthenticationFailureHandler());

        // 添加认证失败后的处理器
        // http.authenticationProvider(myAuthenticationProvider);

        // AccessDeniedException(权限异常)
        // 比如 @PreAuthorize("hasRole('ROLE_HELLO')") 不过，没有角色\权限点的时候的异常
        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);

        // AuthenticationException（认证异常）
        // BadCredentialsException:登录凭证（密码）异常\UsernameNotFoundException:用户名不存在异常
        // \CredentialsExpiredException:登录凭证（密码）过期异常等


        // 写了以后，当没有登陆时候，不再自动跳转login页面，而是走了这一块逻辑
        // 也就是说这个异常包括了没有登陆时候抛出的异常的处理
        // 注册了自己的处理器当访问 http://localhost:8080/login 的时候会直接抛出 Whitelabel Error Page
        // http.exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint);

        // 关闭表单登陆功能 - 没关闭的时候会自动在没权限的时候跳转到form表单
        // http.formLogin().disable();

        // 开启自动配置的注销的功能
        http.logout();
        // 开启记住我的功能
        http.rememberMe();
        // 关闭csrf功能:跨站请求伪造,默认只能通过post方式提交 logout请求
        // csrf().disable()：csrf功能默认会开启，用于防止跨站伪造请求，以后会讲，现在先禁用，否则将无法登录
        http.csrf().disable();
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
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
        // 基于内存添加用户名和密码
//         auth.inMemoryAuthentication()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("admin")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                 .authorities("test")
//                .roles("admin","user");
        auth.authenticationProvider(myAuthenticationProvider)
                .authenticationProvider(userSmsAuthenticationProvider);
    }

}