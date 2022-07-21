package com.example.one.lang;


import com.example.one.mapper.UserMapper;
import com.example.one.po.TestTimeZone;
import com.example.one.po.User;
import com.example.one.service.MongoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class LangApplicationTest {

    //@Autowired
    //private MessageSource messageSource;

    @Test
    public void testI18n() {
        //String str = messageSource.getMessage("hello.world", new String[] {"嘻嘻"}, Locale.CHINA);
        //String str1 = messageSource.getMessage("hello.world", new String[] {"嗯嗯"}, Locale.US);
        //log.info("i18n测试：{}, {}", str,str1);
    }

    @Test
    public void testJsonTimeZone() throws JsonProcessingException {
        TestTimeZone t = new TestTimeZone();
        // 存储的是GMT+8的当前时间
        t.setBirthday(new Date());
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(t);
        // 输出的是GMT+0的当前时间
        // Jackson的带时区的时间格式化
        System.out.println(s);
    }



    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Test
    public void testSelect() throws JsonProcessingException {
        List<User> userList = userMapper.selectList(null);
        // Assert.assertEquals(3, userList.size());
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(userList);
        // birthday datetime
        // workday timestamp
        for (User u : userList){
            System.out.println(u.getBirthday());
        }
        for (User u : userList){
            System.out.println(u.getWorkday());
        }
        System.out.println(s);
    }

    @Test
    public void testInsert() {
        User u = new User();
        u.setAge(18);
        u.setName("hello");
        u.setEmail("123@qq.com");
        u.setWorkday(new Date());
        u.setBirthday(new Date());
        userMapper.insert(u);
    }


    @Test
    public void testZone() {
        TimeZone timeZone = Calendar.getInstance().getTimeZone();
        System.out.println(timeZone);
        // Asia/Shanghai
        System.out.println(timeZone.getID());
        // Thu Apr 14 23:09:22 CST 2022
        System.out.println(new Date());
        TimeZone t = TimeZone.getTimeZone("UTC");
        TimeZone.setDefault(t);
        // Thu Apr 14 15:09:22 UTC 2022
        System.out.println(new Date());
    }

    @Autowired
    private MongoService mongoService;

    @Test
    public void testMongoDB(){
        mongoService.getPerson();
    }
}