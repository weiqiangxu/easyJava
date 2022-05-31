package com.example.one.thread;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 验证线程池
public class TransmittableTest {

    public static void main(String[] args) {

        // ExecutorService详解 https://zhuanlan.zhihu.com/p/65556060
        // Spring自带的线程池ThreadPoolTaskExecutor https://zhuanlan.zhihu.com/p/346086161


        /* 该方法返回一个固定线程数量的线程池，该线程池池中的线程数量始终不变。
         * 当有一个新的任务提交时，线程池中若有空闲线程，则立即执行。
         * 若没有，则新的任务会被暂存在一个任务队列中，待有线程空闲时，便处理在任务队列中的任务
         * 默认等待队列长度为Integer.MAX_VALUE
         */
        // 获取一个数量为2的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Runnable task_test = () -> {
            try {
                System.out.println(Thread.currentThread().getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        // 创建2个线程 - 明显这两个线程是没有上下文context
        // 毕竟context在下面才会创建 - 只有新创建线程的时候才会将context传递进去
        // 由于下面是复用线程池 - 所以不会有上下文 context
        executorService.submit(task_test);
        executorService.submit(task_test);
        // 额外的处理，生成修饰了的对象executorService
        // 如果不加上这一行就会无法获取到线程上下文
        // 修饰一下线程池 - 就可以获取到了
        executorService = TtlExecutors.getTtlExecutorService(executorService);
        TransmittableThreadLocal<String> context = new TransmittableThreadLocal<>();
        context.set("hello world");
        Runnable task = () -> {
            try {
                System.out.println(Thread.currentThread().getId());
                System.out.println("parent:" + context.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        executorService.submit(task);
    }
}
