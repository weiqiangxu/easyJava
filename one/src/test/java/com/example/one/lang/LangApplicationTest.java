package com.example.one.lang;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

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
}