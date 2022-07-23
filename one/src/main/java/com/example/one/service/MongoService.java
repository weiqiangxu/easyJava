package com.example.one.service;


import com.example.one.po.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class MongoService implements MongoServiceInterface {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public int insertStudent(Student student) {
        try {
            mongoTemplate.insert(student);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int updateStudent(Student student) {
        //通过query根据id查询出对应对象，通过update对象进行修改
        Query query = new Query();
        Update update = new Update().set("username", "");
        try {
            mongoTemplate.updateFirst(query, update, Student.class);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int removeStudent(Long id) {
        Query query = new Query(Criteria.where("_id").is(id));
        try {
            mongoTemplate.remove(query, Student.class);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Student findOne(Integer id) {
        Query query = new Query();
        Student one = mongoTemplate.findOne(query, Student.class);
        System.out.println(one);
        return one;
    }

}
