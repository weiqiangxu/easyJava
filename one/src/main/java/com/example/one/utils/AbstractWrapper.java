package com.example.one.utils;

// AbstractWrapper 继承了 Wrapper
// 其中 Wrapper 实现了 Compare 和 Join
public abstract class AbstractWrapper<T, R, Children extends AbstractWrapper<T, R, Children>> extends Wrapper<T>
        implements Compare<Children, R>, Join<Children> {
}
