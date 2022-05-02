package com.example.one;

import com.example.one.kafka.Receiver;
import com.example.one.kafka.Sender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// spring boot的单元测试只需要2个:@SpringBootTest 和 @Test
@SpringBootTest
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
