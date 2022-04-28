package com.example.one.utils;

import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

// 学习 JAVA8的函数式接口
// 函数式接口--Consumer
// 相关博客 https://www.jianshu.com/p/63771441ba31
public class ConsumerTest {

    public static void main(String[] args) {
        testConsumer();
        testAndThen();

        Consumer<? super Integer> doubleNumber = x -> {
            System.out.println( x + x);
        };
        List<Integer> t = new ArrayList<Integer>();
        t.add(1);
        ConsumerTest.<Integer>forEach((Consumer<? super Integer>) doubleNumber, t);
    }

    /**
     * 一个简单的平方计算
     */
    public static void testConsumer(){
        // 定义一个函数式接口的实现
        // 当然你可以把它当成一个闭包
        // 他返回的是一个function  当然准确的来说是一个函数
        Consumer<Integer> square = x -> System.out.println("print square : " + x * x);
        // 这个函数使用 accept 方法去传递参数 - 当然也像是一个闭包
        square.accept(2);
    }

    /**
     * 定义3个Consumer并按顺序进行调用andThen方法，其中consumer2抛出NullPointerException。
     */
    public static void testAndThen(){
        // 定一个函数式接口的实现 - 其实可以当成一个闭包
        Consumer<Integer> consumer1 = x -> System.out.println("first x : " + x);
        Consumer<Integer> consumer2 = x -> {
            System.out.println("second x : " + x);
            // throw new NullPointerException("throw exception test");
        };
        Consumer<Integer> consumer3 = x -> System.out.println("third x : " + x);

        // 定义了3个函数式子接口的实现可以 按顺序调用 andThen,
        // 很明显 后面的传递参数的function accept 传递的参数是一样的
        consumer1.andThen(consumer2).andThen(consumer3).accept(1);
    }



    // 下面是官方JDK的实现的一个转换
    // 理解一下就是传递了两个参数
    // 第一个参数: 传递一个函数式接口的实现
    // 第二个参数：传递 list 数组 - 范型
    // 循环迭代执行
    static <T> void forEach(Consumer<? super T> action, List<T> list) {
        Objects.requireNonNull(action);
        for (T t : list) {
            action.accept(t);
        }
    }

    /**
     * Performs the given action for each element of the {@code Iterable}
     * until all elements have been processed or the action throws an
     * exception.  Unless otherwise specified by the implementing class,
     * actions are performed in the order of iteration (if an iteration order
     * is specified).  Exceptions thrown by the action are relayed to the
     * caller.
     *
     * @implSpec
     * <p>The default implementation behaves as if:
     * <pre>{@code
     *     for (T t : this)
     *         action.accept(t);
     * }</pre>
     *
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     * @since 1.8
     */


}

