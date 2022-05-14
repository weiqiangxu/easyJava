package com.example.one;

import com.example.one.kafka.Sender;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class OneApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private Sender sender;

    // 单元测试Sender
    @Test
    void testSender(){
        sender.send("Spring Kafka Producer and Consumer Example");
        System.out.println("hhh");
    }


}
