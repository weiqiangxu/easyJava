package com.example.one.po;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class Person {

    // @JSONField - Field -  Setter 和 Getter 方法
    // FastJson 在进行操作时，是根据 getter 和 setter 的方法进行的，并不是依据 Field 进行。
    @JSONField(name = "AGE")
    private int age;

    @JSONField(name = "FULL NAME")
    private String fullName;

    @JSONField(name = "DATE OF BIRTH")
    private Date dateOfBirth;

    public Person(int age, String fullName, Date dateOfBirth) {
        super();
        this.age = age;
        this.fullName= fullName;
        this.dateOfBirth = dateOfBirth;
    }

    // 配置date序列化和反序列使用yyyyMMdd日期格式
    @JSONField(format="yyyyMMdd")
    public Date workDate;

    // 使用 serialize/deserialize 指定字段不序列化
    @JSONField(serialize=false)
    public Date startDate;
    // 标准 getters & setters
}
