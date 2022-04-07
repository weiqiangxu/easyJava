package com.example.one.handle;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {

    // 注册一个类到spring之中(类名是PasswordEncoder)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(14); //指定4-31位的长度
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("---用户信息验证----"+username);

        // 添加角色
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        roles.add("test");
        for (String role : roles) {
            Assert.isTrue(!role.startsWith("ROLE_"), () -> role
                    + " cannot start with ROLE_ (it is automatically added)");
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        List<GrantedAuthority> authList = new ArrayList<>(authorities);

        // 添加权限点
        List<GrantedAuthority> permissionList = AuthorityUtils.createAuthorityList("test","del","add");
        authList.addAll(permissionList);
        /**
         isEnabled 账户是否启用
         isAccountNonExpired 账户没有过期
         isCredentialsNonExpired 身份认证是否是有效的
         isAccountNonLocked 账户没有被锁定
         */
        String p = passwordEncoder.encode("123");
        System.out.println("password encode "+p);
        User u =  new User(username, p,
                true,
                true,
                true,
                true,
                authList);

        System.out.println("my user detail auth "+u.getAuthorities().toString());
        return u;
    }



}
