package com.example.one.utils;


public class LambdaQueryWrapper<T> extends AbstractLambdaWrapper<T, LambdaQueryWrapper<T>>
        implements Query<LambdaQueryWrapper<T>, T, String> {


    @Override
    public LambdaQueryWrapper<T> gt(String column, Object val) {
        return super.gt(column, val);
    }

    @Override
    public LambdaQueryWrapper<T> gt(boolean condition, String column, Object val) {
        return null;
    }

    public static void main(String[] args) {
        LambdaQueryWrapper<Integer> c = new LambdaQueryWrapper<>();
        c.gt("name", 1);
    }
}

