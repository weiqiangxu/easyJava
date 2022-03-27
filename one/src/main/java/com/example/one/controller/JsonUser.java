package com.example.one.controller;

import org.springframework.web.bind.annotation.*;

// @Controller 的衍生注解 - @Controller == 处理 Http 请求
@RestController
// 拦截路径，比如说在本地启动服务则在浏览器中输入的url是：localhost:8080/user
@RequestMapping("/user")
// @RequestMapping如果没有指定请求方式，将接收Get、Post、Head、Options等所有的请求方式.
public class JsonUser {
    @GetMapping("/detail/{id}")
    // @PathVariable 处理请求 url 路径中的参数 /user/{id}
    // @RequestParam - 处理问号后面的参数 - （可以配置3个参数 defaultValue、required、value）
    // @RequestBody 请求参数以json格式提交
    // @ResponseBody 返回 json 格式
    // @RestController 是 @Controller 和 @ResponseBody 两个注解的结合体。
    // @ResponseBody会把结果集转为json所以如果是返回视图，只需要使用@Controller
    public Integer get(@PathVariable("id") Integer id){
        return id;
    }

    // PostMapping相当于是@RequestMapping(method = RequestMethod.POST)的缩写
    @PostMapping("/add")
    // @requestBody(后端方法接收请求体)
    // @requestBody注解常用来处理content-type不是默认的application/x-www-form-urlcoded编码的内容，比如说：
    // application/json或者是application/xml等。一般情况下来说常用其来处理application/json类型。

    // @RequestPart
    // @RequestPart这个注解用在multipart/form-data表单提交请求的方法上。多用于文件上传场景。
    public Integer add(@RequestBody String name,Integer age){
        return age;
    }

    // Post的参数接收方式：
    // 1、构造java对象接收
    // 开启验证需要在对象属性里面添加验证规则 - 但是在参数括号可以不加@valid - 在类属性有valid验证规则就会触发
    // 除非特别要求，否则直接java对象形式接收参数就可以了，
    // 特别要求有： @requestBody String userName，只需要json之中的userName字段名参数并且直接映射到字段
    // 这里接收参数以及格式校验、还有防止异常Exception
}
