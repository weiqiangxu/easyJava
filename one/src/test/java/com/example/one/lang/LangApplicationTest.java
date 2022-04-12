package com.example.one.lang;


import com.example.one.mapper.UserMapper;
import com.example.one.po.TestTimeZone;
import com.example.one.po.User;
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

import java.util.Date;
import java.util.List;
import java.util.Locale;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class LangApplicationTest {

    @Autowired
    private MessageSource messageSource;

    @Test
    public void testI18n() {
        String str = messageSource.getMessage("hello.world", new String[] {"嘻嘻"}, Locale.CHINA);
        String str1 = messageSource.getMessage("hello.world", new String[] {"嗯嗯"}, Locale.US);
        log.info("i18n测试：{}, {}", str,str1);
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
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(userList);
        System.out.println(s);
    }
}