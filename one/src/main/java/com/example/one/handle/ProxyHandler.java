package com.example.one.handle;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {

    private final Object target;

    public ProxyHandler(Object target) {
        this.target = target;
    }

    // 1.实现代理类
    // 2.被代理的对象注入进去
    // 3.

    // JVM 的动态代理是一种在运行时动态地创建代理对象的机制, 面向切面编程（AOP）的一种实现方式
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 实现一个动态代理
        System.out.println("Before calling target method.");
        Object result = method.invoke(target, args);
        System.out.println("After calling target method.");
        return result;
    }
}
