package com.example.one.po;

import com.example.one.annotation.Cat;
import lombok.Getter;

@Cat(name="tom")
public class Tomcat {

    @Getter
    private String name;

    public Tomcat(){

    }

    public Tomcat(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tomcat{" +
                "name='" + name + '\'' +
                '}';
    }
}