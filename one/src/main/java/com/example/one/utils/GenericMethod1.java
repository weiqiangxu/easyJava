package com.example.one.utils;

/**
 * Author：Jay On 2019/5/10 10:46
 * <p>
 * Description: 泛型方法
 */
public class GenericMethod1 {
    private static int add(int a, int b) {
        System.out.println(a + "+" + b + "=" + (a + b));
        return a + b;
    }

    // 定义的形式记住就可以了
    // 类的是在类名增加<T>
    // 方法是在 方法名称之前定一个 T ,调用时候传入T


    //    所以范型也不只是 T
    //    E - Element (在集合中使用，因为集合中存放的是元素)
    //    T - Type（Java 类）
    //    K - Key（键）
    //    V - Value（值）
    //    N - Number（数值类型）
    //    ？ - 表示不确定的 java 类型

    // 范型方法通常指的是 形参数的类型使用 <T> 修饰方法
    // 同时传入func的参数类型也是T
    // 准确的来说 E 也可以 N 也可以是范型

    private static <T> T genericAdd(T a, T b) {
        System.out.println(a + "+" + b + "="+a+b);
        return a;
    }

    public static void main(String[] args) {
        GenericMethod1.add(1, 2);
        GenericMethod1.<String>genericAdd("a", "b");
    }
}