package com.example.one.handle;


import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

public class CglibExample {
    // CGlib动态代理的没有跑起来
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserServiceImpl.class);
        enhancer.setCallback(new CglibProxy());
        UserServiceImpl proxyObject = (UserServiceImpl) enhancer.create();
        proxyObject.saveUser("jack");
    }
}


