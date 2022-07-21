package com.example.one.service;

import com.example.one.po.Person;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
@Service
public class MongoService {

    public void getPerson(){
        MongoOperations m = new MongoTemplate(MongoClients.create(), "database");
        m.insert(new Person(1, "jack",new Date()));
        Person p = m.findOne(new Query(where("name").is("Joe")), Person.class);
        log.info("p:{}",p);
    }
}
