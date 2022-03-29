package com.example.one.controller;

import com.example.one.exception.CustomGenericException;
import com.example.one.po.Employee;
import com.example.one.po.Work;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/valid/user/")
@RestController
public class ValidUser {
    /**
     * 注解实现
     * 使用@Valid 注解 实体， 并传入参数bindResult以获取校验结果信息
     * @param employee
     * @return
     */
    @GetMapping("/adds")
    public String addEmployee(Employee employee){
        // 对象形式接收参数
        // 对象属性有验证规则
        // 验证规则不生效
        //
        // 前端访问链接是  /adds?name=jack&age=18
        // 其中name和age都会放进employee的对象之中
        return "add success "+employee.toString();
    }


    // 演示一个最简单的验证写法
    // 也是最原始的验证写法
    @GetMapping("/add")
    public String add(Employee employee){
        if(employee.getName() == ""){
            // 字段不能为空 - 验证逻辑
            // 这也是最原始的数据校验
            return "error";
        }
        return "add success "+employee.toString();
    }


    // 使用@Valid让对象接收参数的
    // 对象里面的属性验证规则生效
    // 并且捕捉错误信息
    @GetMapping("/addTwo")
    public String addTwo(@Valid Employee employee, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return bindingResult.getAllErrors().get(0).getDefaultMessage();
        }
        return "add success "+employee.toString();
    }


    // {"company":{"companyName":"123"},"location":"beijing"} -- 传递数据
    // @RequestBody赋值非常容易exception

    // 使用@RequestBody将json参数复制给Work对象 --- 只要没有json参数就会抛出异常
    // Resolved [org.springframework.http.converter.HttpMessageNotReadableException:
    // Required request body is missing: public java.lang.String com.example.one.controller.ValidUser.work
    // (com.example.one.po.Work,org.springframework.validation.BindingResult)]
    // 直接就会抛出异常 --  只要没有匹配上的json参数
    // 但是只要post过来的json存在，哪怕是一个空json -- 也会得到正常的参数验证信息
    @PostMapping("/work")
    public String work(@Validated @RequestBody Work work, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return bindingResult.getAllErrors().get(0).getDefaultMessage();
        }
        return "add success "+work.toString();
    }


    // 这里抛出异常将会被@ControllerAdvice注册的统一异常处理接收异常
    @PostMapping("/workTwo")
    public String workTwo(@Validated @RequestBody Work work, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return bindingResult.getAllErrors().get(0).getDefaultMessage();
        }
        return "add success "+work.toString();
    }



    @GetMapping("/hello")
    public String hello(){
        return "hello springframework.validation";
    }
}