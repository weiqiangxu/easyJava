package com.example.one.po;

public class SetUserIp implements OptionFunc{

    private String userIp;

    @Override
    public void set(LoggerDetail loggerDetail) {
        loggerDetail.setUserIp(userIp);
    }

    public SetUserIp(String userIp){
        this.userIp = userIp;
    }
}
