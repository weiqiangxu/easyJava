package com.example.one.controller;

import com.example.one.po.Car;
import com.example.one.po.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

// @RestController相当于Controller加上ResponseBody
// 定义为控制器
@Controller
// 返回 json 格式
@ResponseBody
// 这个注解相当于这个下面所有function的路由添加前缀,如果是@GetMapping就仅仅会对get请求的前缀路由添加
@RequestMapping("/json/car")
// 文档地址 https://juejin.cn/post/6844904166809157639
public class JsonCar {

    // 功能合集：
    // 1、JSON字符串-->Java对象
    // 2、JSON 字符输入流（Reader对象）-->Java对象
    // JSON文件-->Java对象
    // JSON via URL--->Java对象
    // JSON字节输入流（InputStream对象，可以通过创建文件stream获取 - new FileInputStream("data/car.json");）-->Java对象
    // JSON二进制数组（byte[]对象转换）-->Java对象
    // JSON数组字符串-->Java对象数组
    // JSON数组字符串-->List
    // JSON字符串-->Map -- 可以直接获取一个Map<String, Object>对象


    // 学习一个配置
    // objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
    // 当java对象属性为基本数据类型（int double之类），如果解析为java对象用的字符串没有这个属性
    // 设置了这个值将会抛出exception


    // Java对象-->JSON === 可以将Java对象换成一个[]byte或者string，甚至直接写入文件 - new FileOutputStream("data/output-2.json")



    @GetMapping("/get")
    // 试试jackson将字符串转java对象
    public String get(){
        ObjectMapper objectMapper = new ObjectMapper();
        // JSON字符串无可避免的需要转义
        String carJson ="{ \"brand\" : \"Mercedes\", \"doors\" : 5 }";
        Car car = new Car();
        try {
            car = objectMapper.readValue(carJson, Car.class);
            System.out.println("car brand = " + car.getBrand());
            System.out.println("car doors = " + car.getDoors());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return car.getBrand();
    }


    // 对于java对象之中的Data类型属性
    // 默认的Jackson日期格式，该格式将Date序列化为自1970年1月1日以来的毫秒数（long类型）
    // 注意是毫秒数
    @GetMapping("/data")
    public String testJsonData() throws JsonProcessingException {
        Transaction transaction = new Transaction("transfer", new Date());
        ObjectMapper objectMapper = new ObjectMapper();
        String output = objectMapper.writeValueAsString(transaction);
        return output;
    }

    // jackson可以对日期设置默认的解析规则
    // 解析为固定的字符串格式
    @GetMapping("/date")
    public String testJsonDate() throws JsonProcessingException {
        Transaction transaction = new Transaction("transfer", new Date());
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        objectMapper.setDateFormat(dateFormat);
        String output2 = objectMapper.writeValueAsString(transaction);
        return output2;
    }


    // 验证一下@ResponseData有没有对exception捕获处理
    @GetMapping("/exception")
    public void testException() throws Exception {
        // 很明显是没有的
        // 会直接响应 Http Status Code: 500
        throw new Exception("fail");
    }


    // JsonGenerator
    // Jackson JsonGenerator用于从Java对象（或代码从中生成JSON的任何数据结构）生成JSON。


    // Jackson注解
    // @JsonProperty
    // @JsonFormat - 用于属性或者方法 - 把属性的格式序列化时转换成指定的格式
    // @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    // --- 指定时区非常重要，没有指定将会以UTC解析时间
    // Unix 时间戳是从 1970-1-1 00:00:00 UTC （北京时间1970-01-01 08:00:00）开始所经过的秒数
    // 同样一个时间戳数字，以UTC时区解析的话，时间将会小一点，比如同样过去了 86400s（一天）
    // UTC的话是0点，而北京时间是8点

    // 上次遇到的问题：linux服务器的时间是北京时间 2022-03-28 10:00:00
    // 对于 new Date()（GTM+8）获取的也是对应的时间戳假定86400 - 对于MySQL（DateTime数据格式）存储的时间也是对的GTM+8的
    // 但是获取到的时间以后，转为时间戳，jackson解析的时候以UTC的时区解析，时间就小了

    // 复习MySQL
    // TIMESTAMP值范围从1970-01-01 00:00:01 UTC到2038-01-19 03:14:07 UTC。
    // 如果要存储超过2038的时间值，则应使用DATETIME而不是TIMESTAMP

    // DATETIME的值范围为1000-01-01 00:00:00至9999-12-31 23:59:59
    // 就是以多个数字组成

    // @JsonPropertyOrder
    // @JsonCreator
    // @JsonAnySetter
    // @JsonAnyGetter


}
