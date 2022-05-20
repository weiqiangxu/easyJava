package com.example.one.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

// 示例地址: https://www.yiibai.com/kafka/spring-kafka-consume-producer.html
//@Service
public class Sender {

    private static final Logger LOG = LoggerFactory.getLogger(Sender.class);

//    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;



    public void send(String message){
        LOG.info("sending message='{}' to topic='{}'", message, "mykafka");
        kafkaTemplate.send("mykafka", message);
    }
}
