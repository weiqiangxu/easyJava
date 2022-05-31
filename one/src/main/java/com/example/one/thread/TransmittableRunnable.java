package com.example.one.thread;


import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// 验证修饰Runnable
public class TransmittableRunnable {

    // 0.创建线程池
    private static final ThreadPoolExecutor bizPoolExecutor = new ThreadPoolExecutor(
            2, 2, 1, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(1));


    public static void main(String[] args) throws InterruptedException {
        // 1 创建线程变量
        TransmittableThreadLocal<String> parent = new TransmittableThreadLocal<>();
        // 2 投递三个任务
        for (int i = 0; i < 3; ++i) {
            bizPoolExecutor.execute(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        // 3休眠4s
        Thread.sleep(4000);
        // 4.设置线程变量
        parent.set("hello world");
        // 5. 提交任务到线程池
        Runnable task = () -> {
            try {
                // 5.1访问线程变量
                System.out.println("parent:" + parent.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        // 5.2额外的处理，生成修饰了的对象ttlRunnable
        Runnable ttlRunnable = TtlRunnable.get(task);
        //5.3
        bizPoolExecutor.execute(ttlRunnable);

        bizPoolExecutor.execute(() -> {
            try {
                // 由于没有被修饰过所以无法获取到变量
                System.out.println("parent:" + parent.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

