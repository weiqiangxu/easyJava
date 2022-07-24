package com.example.one.listener;

import cn.hutool.json.JSONUtil;
import com.example.one.po.Student;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

/**
 * 生命周期事件
 * https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongodb.mapping-usage.events
 */
@Configuration
public class BeforeConvertListener extends AbstractMongoEventListener<Object> {
    @Override
    public void onAfterConvert(AfterConvertEvent<Object> event) {
        System.out.println("before ");
        System.out.println(Object.class.getName());
        System.out.println(event.getCollectionName());
        System.out.println(JSONUtil.toJsonStr(event));
        // com.example.one.po.Student
        System.out.println(event.getSource().getClass().getName());
        System.out.println(event.getSource().getClass().toGenericString());
    }
}