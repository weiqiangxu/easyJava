package com.example.one.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.one.po.ApiResponse;
import com.example.one.po.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// alibaba fast json学习
@RestController
@RequestMapping("/school")
public class School {

    // 对象转为json字符串
    @GetMapping("/get")
    public String get(){
        List<Person> listOfPersons = new ArrayList<Person>();
        listOfPersons.add(new Person(15, "John Doe", new Date()));
        listOfPersons.add(new Person(20, "Janette Doe", new Date()));
        // 可以使用 JSON.toJSONString() 将 Java 对象转换换为 JSON 对象
        String jsonOutput= JSON.toJSONString(listOfPersons);
        return jsonOutput;
    }

    // json对象数组创建
    @GetMapping("/testJSONArray")
    public void testJSONArray(){
        // 创建 JSON 对象
        // JSONObject（fastJson提供的json对象） 和 JSONArray（fastJson提供json数组对象） 对象即可
        // 创建一个对象
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < 2; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("AGE", 10);
            jsonObject.put("FULL NAME", "Doe " + i);
            jsonObject.put("DATE OF BIRTH", "2016/12/12 12:12:12");
            jsonArray.add(jsonObject);
        }
        String j = jsonArray.toJSONString();
        System.out.println(j);
    }

    // 字符串转为java对象
    @GetMapping("/testStringToObject")
    public String testStringToObject(){
        Person person = new Person(20, "John", new Date());
        String jsonObject = JSON.toJSONString(person);
        Person newPerson = JSON.parseObject(jsonObject, Person.class);
        // 解析为无匹配的对象
        ApiResponse api = JSON.parseObject(jsonObject,ApiResponse.class);
        // ApiResponse(code=null, message=null, data=null)
        // 字符串和java对象完全不匹配的时候也不会炸，只是全部会设置为默认零值
        System.out.println(api.toString());
        return api.getMessage();
    }
}
