package com.example.one.service;

import com.example.one.annotation.OperationLogAnno;
import com.example.one.annotation.Person;
import com.example.one.po.Human;
import com.example.one.po.OperationType;
import com.example.one.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@Service
@Slf4j
public class SchoolService {

//    @OperationLogAnno
    @OperationLogAnno(operatePage = "testPage",operateType = "testType",bizType = "/aspect/update",operationType = OperationType.Write)
    public String getSchoolName(Integer schoolID){
        return "jack";
    }

    @OperationLogAnno
    public String testSchoolName(User user){
        return "jack";
    }


    // 使用注解给类快速赋值
    @Person(name="张三", sex = "男", age = 23)
    private Human human;

    public String getSchool(){
        return human.toString();
    }

    // 注解实现
    // 需要手动调用
    // 反射形式查找当前使用了Person注解的属性
    // 寻找到使用了注解的对象
    // 获取当前属性的对象逐个赋值为注解的值
    public void initField() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            Person person = field.getDeclaredAnnotation(Person.class);
            if(person != null) {
                Human human = ((Class<Human>) field.getType()).getDeclaredConstructor().newInstance();
                human.setSex(person.sex());
                human.setName(person.name());
                human.setAge(person.age());
                field.set(this, human);
            }
        }
    }


}
