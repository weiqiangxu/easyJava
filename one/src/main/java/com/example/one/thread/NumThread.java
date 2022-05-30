package com.example.one.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/**
 * https://blog.csdn.net/weixin_53870975/article/details/122568133
 * 创建线程的第三种方法
 * 实现Callable接口  jdk5.0新增
 * 步骤
 * 1.创建一个实现类Callable的实现类
 * 2.实现Call方法
 * 3.创建Callable实现类接口的对象
 * 4.将此Callable接口实现类对象作为参数传递到FutureTask构造器中
 * 5.将FutureTask的对象作为参数传递到Thread类的构造器中，创建Thread对象，并调用start()
 * @Author:阿土伯
 * @Date: 2022/1/17 11:40
 * 如何理解实现Callable接口的方式创建多线程比实现Runnable接口创建多线程方式强大？
 * 1.call()可以有返回值
 * 2.call()可以抛出异常，被外面的操作捕获，获取异常信息
 * 3.Callable是支持泛型的
 */
//1.创建一个实现类Callable的实现类
class NumThread implements Callable {
    //2.实现call方法
    @Override
    public Object call() throws Exception {
        int sum = 0;
        for (int i = 0; i <= 100; i++) {
            if (i % 2 == 0) {
                System.out.println(i);
                sum += i;
            }
        }
        return sum;
    }

    public static void main(String[] args){
        //3.创建Callable接口实现类对象
        NumThread numThread = new NumThread();
        //4.将此Callable接口实现类对象作为参数传递到FutureTask构造器中
        FutureTask futureTask=new FutureTask(numThread);
        //5.将FutureTask的对象做为参数传递到Thread类的构造器中，创建Thread对象，并调用start()
        new Thread(futureTask).start();
        try {
            Object sum = futureTask.get();
            System.out.println("总和为："+sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}