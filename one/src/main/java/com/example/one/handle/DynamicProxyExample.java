package com.example.one.handle;

import java.lang.reflect.Proxy;

public class DynamicProxyExample {
    public static void main(String[] args) {
        // 这是一个动态代理 - 运行时动态地创建代理对象
        // JVM的动态代理

        // 1. 初始化对象
        // 2. 实现代理类
        // 3. 将被代理的对象注入代理类
        // 4. 代理类执行行为，执行被代理类的行为和代理类的附加动作
        TargetInterface targetObject = new TargetObject();
        TargetInterface proxyObject = (TargetInterface) Proxy.newProxyInstance(
                targetObject.getClass().getClassLoader(),
                targetObject.getClass().getInterfaces(),
                new ProxyHandler(targetObject));
        proxyObject.targetMethod();



        // 1.编译期 - .java变成了.class - 修改了字节码

        // 例子：  AOP 工具（如 AspectJ）来实现切面编程，代码从java编译为字节码的时候加入进去

        // 2.类加载期间 - 字节码加载到JVM - 内存时候修改了内存中的

        // 字节码初始化类，到内存之中的时候，

        // 3.运行期 - 调用的时候我把对象注入到代理对象，不同的调用我可以把对象注入到不同的代理里面，比较灵活

        // 运行期间通过代理对象proxy之类的实现代码的做到


    }
}
