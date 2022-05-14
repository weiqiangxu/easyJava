package com.example.one.service;

import com.example.one.po.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AspectLog {
    public void saveLog(String operate, String type, String methodRoot, OperationType realType, String time) {
        // 保存数据到Elastic或者kafka之中
        System.out.println(operate);
    }
}
