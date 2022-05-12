package com.example.one;

import com.example.one.annotation.Cat;
import com.example.one.kafka.Receiver;
import com.example.one.kafka.Sender;
import com.example.one.po.Dog;
import com.example.one.service.SchoolService;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

// spring boot的单元测试只需要2个:@SpringBootTest 和 @Test

//https://www.zhihu.com/question/24401191
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

    private SchoolService schoolService;

    @Autowired
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    // https://www.bilibili.com/read/cv13282317
    @Test
    void testMyTag() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        schoolService.initField();
        String a = schoolService.getSchool();
        System.out.println(a);
    }

    @Test
    void testCat() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ClassPathScanningCandidateComponentProvider classPathScanningCandidateComponentProvider =
                new ClassPathScanningCandidateComponentProvider(false);
        classPathScanningCandidateComponentProvider.addIncludeFilter(new AnnotationTypeFilter(Cat.class));
        Set<BeanDefinition> beanDefinitions =
                classPathScanningCandidateComponentProvider.findCandidateComponents("com.example.one.po");
        for(BeanDefinition beanDefinition : beanDefinitions){
            String beanClassName = beanDefinition.getBeanClassName();
            Class clazz = Class.forName(beanClassName);
            Cat cat = (Cat) clazz.getDeclaredAnnotation(Cat.class);
            String name = cat.name();
            Object object = clazz.getDeclaredConstructor(String.class).newInstance(name);
            System.out.println(object);
        }
    }

    @Test
    void testReflect(){
        Dog dog = new Dog();
        System.out.println(dog.toString());

    }

    // https://www.cnblogs.com/WJQ2017/p/16025941.html 动手实现lombok的setter和getter
}
