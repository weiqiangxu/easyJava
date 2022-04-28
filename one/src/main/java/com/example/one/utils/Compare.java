package com.example.one.utils;

// 定义了泛型，相当于在class的名称后面<>括号内部定义2个形参
// 这两个形参就是修饰符，可以传递任意java Type定义这个class里面的具体类


// Compare 被抽象类 AbstractWrapper 实现 \  AbstractWrapper又被 AbstractLambdaWrapper 继承 \ LambdaQueryWrapper又继承了 AbstractLambdaWrapper
// 继承的链路  AbstractWrapper > AbstractLambdaWrapper > LambdaQueryWrapper
// 相当于 Compare 有了 3个 实现


// 最终我使用Query类库的时候使用了 LambdaQueryWrapper 的实例
// 而LambdaQueryWrapper在定义的时候定义了AbstractWrapper的第二个java Type是String
// 相当于Compare的泛型第二个类型的R是String

public interface Compare<Children, R> {

    /**
     * ignore
     */
    default Children gt(R column, Object val) {
        return gt(true, column, val);
    }

    /**
     * 大于 &gt;
     *
     * @param condition 执行条件
     * @param column 字段
     * @param val 值
     * @return children
     */
    Children gt(boolean condition, R column, Object val);
}
