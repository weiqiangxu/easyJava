package com.example.one.handle;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {


    /**
     * 验证用户身份
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println(authentication.getDetails());
        return null;
    }



    /**
     * supports 则用来判断当前的 AuthenticationProvider 是否支持对应的 Authentication。
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
