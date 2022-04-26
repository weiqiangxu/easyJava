package com.example.one.utils;


public class NeedGeneric1 {

    private static int add(int a, int b) {
        System.out.println(a + "+" + b + "=" + (a + b));
        return a + b;
    }

    private static float add(float a, float b) {
        System.out.println(a + "+" + b + "=" + (a + b));
        return a + b;
    }

    private static double add(double a, double b) {
        System.out.println(a + "+" + b + "=" + (a + b));
        return a + b;
    }

    private static <T extends Number> double add(T a, T b) {
        System.out.println(a + "+" + b + "=" + (a.doubleValue() + b.doubleValue()));
        return a.doubleValue() + b.doubleValue();
    }

    public static void main(String[] args) {
        // 没有范型以前
        NeedGeneric1.add(1, 2);
        // 1f完全相同，表示以1.0文字形式表示的数字float
        NeedGeneric1.add(1f, 2f);
        // 面试题：单精度和双精度有什么区别
        // 表层：单精度占4个字节, 有效数字8位
        // 双精度占8个字节,有效数字16位
        // 深层,https://www.cnblogs.com/quincy-qiu/archive/2013/04/18/4014359.html
        NeedGeneric1.add(1d, 2d);
        NeedGeneric1.add(Integer.valueOf(1), Integer.valueOf(2));
        NeedGeneric1.add(Float.valueOf(1), Float.valueOf(2));
        NeedGeneric1.add(Double.valueOf(1), Double.valueOf(2));
    }
}