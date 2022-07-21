package com.example.one.service;

import com.example.one.po.Student;

public interface MongoServiceInterface {

    int insertStudent(Student student);

    int updateStudent(Student student);

    int removeStudent(Long id);

    Student findOne(Integer id);
}
