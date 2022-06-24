package com.jeeplus.security.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
public class SecurityUtils {

    /**
     * 描述根据账号密码进行调用security进行认证授权 主动调
     * 用AuthenticationManager的authenticate方法实现
     * 授权成功后将用户信息存入SecurityContext当中
     * @param username 用户名
     * @param password 密码
     * @param authenticationManager 认证授权管理器,
     * @see  AuthenticationManager
     * @return UserInfo  用户信息
     */
    public static Authentication login(String username, String password, AuthenticationManager authenticationManager) throws AuthenticationException {
        //使用security框架自带的验证token生成器  也可以自定义。
        UsernamePasswordAuthenticationToken token =new UsernamePasswordAuthenticationToken(username,password );
        //该方法会去调用userDetailsService.loadUserByUsername()去验证用户名和密码，如果正确，则存储该用户名密码到“security 的 context中”
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        User userInfo = (User) authentication.getPrincipal();
        return authentication;
    }



    /**
     * 获取当前登录的所有认证信息
     * @return
     */
    public static Authentication getAuthentication(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication;
        }

        return null;
    }

    /**
     * 获取当前登录的token
     * @return
     */
    public static String getToken(){

        Authentication authentication = getAuthentication ();
        return authentication != null? authentication.getCredentials ().toString (): null;
    }



    /**
     * 获取当前登录用户名
     * @return
     */
    public static String getLoginName(){
        Authentication authentication = getAuthentication();
        return  authentication != null? authentication.getName (): null;
    }


    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 验证密码是否正确
     * @param plainPassword
     * @param password
     * @return
     */
    public static boolean validatePassword(String plainPassword, String password) {

        return password.equals(encryptPassword ( plainPassword));
    }

}
