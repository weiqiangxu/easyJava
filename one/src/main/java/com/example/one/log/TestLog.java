package com.example.one.log;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestLog {


    // Slf4j是一个日志框架的抽象
    // 而log4j是一个实现
    // https://www.yiibai.com/slf4j/slf4j_vs_log4j.html

    public static void main(String[] args) {
        log.info("hello log");
    }



}
