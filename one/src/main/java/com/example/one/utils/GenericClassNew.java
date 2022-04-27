package com.example.one.utils;

/**
 * Author：Jay On 2019/5/9 16:49
 * <p>
 * Description: 泛型类
 */
// 泛型类的使用 - 其实就是类实例化支持传入类
public class GenericClassNew<T> {

    // 给类定一个属性,类型是实例化时候传入的对象
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static void main(String[] args) {
        // 定义一个范型类
        // 范型类都是定一个data去接收类名后面的 T
        // 实例化的时候传入类型类型也将变为data的类型

        GenericClassNew<String> genericClass=new GenericClassNew<>();
        genericClass.setData("Generic Class");
        System.out.println(genericClass.getData());
    }
}