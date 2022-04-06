package com.example.one.handle;

import com.alibaba.fastjson.JSON;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 自定义GrantedAuthority接口
 */
public class UserGrantedAuthority implements GrantedAuthority {
    private Map<String, Object> authoritys = new HashMap<>();
    public UserGrantedAuthority(String name, Object value){
        authoritys.put(name,value);
    }
    @Override
    public String getAuthority() {
        return JSON.toJSONString(authoritys);
    }
}