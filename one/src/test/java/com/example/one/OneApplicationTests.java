package com.example.one;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.one.kafka.Sender;
import com.example.one.po.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class OneApplicationTests {

    @Test
    void contextLoads() {
    }

//    @Autowired
//    private Sender sender;

    // 单元测试Sender
    @Test
    void testSender(){
//        sender.send("Spring Kafka Producer and Consumer Example");
        System.out.println("hhh");
    }

    @Test
    void testLogger(){
        log.info("info");
    }

    @Test
    public void selectList(){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
//        User::getAge;
        lambdaQueryWrapper.like(User::getBirthday , "k").lt(User::getAge , 30);

    }


}
