package com.example.one.utils;

// AbstractLambdaWrapper 继承了 AbstractWrapper
public abstract class AbstractLambdaWrapper<T, Children extends AbstractLambdaWrapper<T, Children>>
        extends AbstractWrapper<T, String, Children> {

}
