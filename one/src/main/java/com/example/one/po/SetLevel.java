package com.example.one.po;

public class SetLevel implements OptionFunc{

    private String level;
    @Override
    public void set(LoggerDetail loggerDetail) {
        loggerDetail.setLevel(level);
    }
    public SetLevel(String level){
        this.level = level;
    }
}
