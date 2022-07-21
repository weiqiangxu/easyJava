package com.example.one.controller;

import com.example.one.config.ResponseResult;
import com.example.one.po.Student;
import com.example.one.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
@ResponseResult
public class StudentController {

    @Autowired
    private MongoService mongoService;

    @RequestMapping("/get")
    public Student get(){
        return mongoService.findOne(1);
    }

}
