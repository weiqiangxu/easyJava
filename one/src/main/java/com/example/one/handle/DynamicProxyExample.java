package com.example.one.handle;

import java.lang.reflect.Proxy;

public class DynamicProxyExample {
    public static void main(String[] args) {
        TargetInterface targetObject = new TargetObject();
        TargetInterface proxyObject = (TargetInterface) Proxy.newProxyInstance(
                targetObject.getClass().getClassLoader(),
                targetObject.getClass().getInterfaces(),
                new ProxyHandler(targetObject));
        proxyObject.targetMethod();
    }
}
