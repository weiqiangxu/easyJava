package com.example.one.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "student")  //指定要对应的文档名(表名）
public class Student {

    /*** 自定义mongo主键 加此注解可自定义主键类型以及自定义自增规则
     *  若不加 插入数据数会默认生成 ObjectId 类型的_id 字段
     *  org.springframework.data.annotation.Id 包下
     *  mongo库主键字段还是为_id 。不必细究(本文实体类中为id）
     */
    @Id
    private Integer id;
    private String username;
}