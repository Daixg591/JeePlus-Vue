package com.jeeplus.security.jwt;

import cn.hutool.core.util.StrUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * JWT过滤器
 *
 */
public class JWTFilter extends GenericFilterBean {


    private final TokenProvider tokenProvider;

    AuthenticationManager authenticationManager;


    public JWTFilter(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = TokenProvider.resolveToken(request);
        boolean isLogin = StrUtil.isNotBlank ( token )&& !"null".equals(token);

        /**
         * 过滤器逻辑：如果请求中含有token，先验证token是否合法有效。
         * 如果验证token失败，直接放行，由spring security 负责安全拦截，包括未登录访问拦截和 未授权方法拦截。
         * 如果验证token有效，判断是否登录了spring security, 如果未登录，则执行登录security。
         *
         * 你肯定有疑问，为啥token验证失败是放行而不是拒绝访问呢？
         * 原因很简单，因为url是否可以匿名访问是在spring security配置的，比如登录url(/sys/login)可以匿名访问，如果我们拒绝了将不能执行登录操作。
         * 我们集成了spring security，由它负责登录和方法鉴权， 而token仅仅是用来携带用户信息的。所以我们jwt过滤器仅仅是用来获取token信息，判断token是否合法，
         * 如果合法，剩下的登录鉴权和方法鉴权将移交给spring security进行。--by 刘高峰
         *
         */
        if ( isLogin && this.tokenProvider.validateToken(token)) { // 验证token是合法token
            if ( SecurityContextHolder.getContext().getAuthentication() == null) { // 需要登录
                Authentication authentication = this.tokenProvider.getAuthentication ( token );
//                authenticationManager.authenticate(authentication); 验证不需要
                SecurityContextHolder.getContext ( ).setAuthentication ( authentication );
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }



}
