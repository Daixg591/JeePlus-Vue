package com.jeeplus.security.service;

import com.jeeplus.core.errors.ErrorConstants;
import com.jeeplus.sys.constant.CommonConstants;
import com.jeeplus.sys.service.UserService;
import com.jeeplus.sys.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过用户名从数据库获取用户信息
        UserDTO userInfo =  userService.getUserByLoginName ( username );

        // 用户不存在
        if (userInfo == null) {
            throw new AccountExpiredException (ErrorConstants.LOGIN_ERROR_NOTFOUND);
        }

        // 用户禁止登录
        if ( CommonConstants.NO.equals ( userInfo.getLoginFlag ( ) ) ) {
            throw new AccountExpiredException ( ErrorConstants.LOGIN_ERROR_FORBIDDEN );
        }

        // 权限集合
        List <GrantedAuthority> authorities = new ArrayList <> ();

        return new User (
                userInfo.getLoginName (),
                userInfo.getPassword(),
                authorities
        );
    }
}

