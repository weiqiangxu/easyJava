package com.example.one.po;

import lombok.Data;

@Data
public class LoggerDetail {

    private String userName;
    private String userIp;
    private String level;

    public static LoggerDetail builder(String userName,OptionFunc ...optionFunc){
        LoggerDetail loggerDetail = new LoggerDetail();
        loggerDetail.setUserName(userName);
        for (OptionFunc func : optionFunc) {
            func.set(loggerDetail);
        }
        return loggerDetail;
    }
}
