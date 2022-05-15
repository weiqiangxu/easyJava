package com.example.one.annotation;

import annotation.MyGetter;

@MyGetter
public class TestMyGetter {
    public String name = "michael";

    public static void main(String[] args){
        TestMyGetter p = new TestMyGetter();
//        System.out.println(p.getName());
    }
}
