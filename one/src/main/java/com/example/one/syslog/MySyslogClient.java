package com.example.one.syslog;

import com.alibaba.fastjson.JSONObject;
import org.graylog2.syslog4j.Syslog;
import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.SyslogIF;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

public class MySyslogClient {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 32376;

    public void generate() {
        SyslogIF syslog = Syslog.getInstance(SyslogConstants.UDP);
        syslog.getConfig().setHost(HOST);
        syslog.getConfig().setPort(PORT);

        StringBuffer buffer = new StringBuffer();
        buffer.append("time:" + new Date().toString().substring(4,20) + ";")
                .append("hello:" + "world" + ";")
                .append("eat:" + "something" + ";");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", buffer.toString());
        try {
            syslog.log(0, URLDecoder.decode(jsonObject.toString(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println("generate log get exception " + e);
        }
    }
    
    public static void main(String[] args) {
        new MySyslogClient().generate();
    }
}