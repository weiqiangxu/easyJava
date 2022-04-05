package com.example.one.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class TestSecurity {
    @GetMapping("/get")
    public String get(){
        return "hello,security";
    }

    @GetMapping("/test")
    public String test(){
        return "hello,security test";
    }

    // @PreAuthorize 注解的异常，抛出AccessDeniedException异常，
    // 不会被accessDeniedHandler捕获，而是会被全局异常捕获 - 就会导致accessDenyHandler失效
    @PreAuthorize("hasRole('ROLE_HELLO')")
    @RequestMapping("/hello")
    public String hello(){
        return "HELLO";
    }
}
