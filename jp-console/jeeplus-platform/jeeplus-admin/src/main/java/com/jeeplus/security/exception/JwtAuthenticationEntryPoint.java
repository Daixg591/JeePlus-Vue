package com.jeeplus.security.exception;

import com.jeeplus.core.errors.ErrorConstants;
import com.jeeplus.security.jwt.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * 认证失败处理类，返回401
 * Author: liugaofeng
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        //验证为未登陆状态会进入此方法，认证错误
        String errMsg;
        if( StringUtils.isEmpty ( TokenProvider.resolveToken ( request ) ) ){ // 没有token抛出的异常
            errMsg = ErrorConstants.LOGIN_ERROR_NOT_LOGIN;
            response.setStatus( HttpStatus.UNAUTHORIZED.value () );
        }else { // token过期抛出的异常
            errMsg = ErrorConstants.LOGIN_ERROR_TIMEOUT;
            response.setStatus( HttpStatus.REQUEST_TIMEOUT.value () );
        }
        log.error (errMsg);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(errMsg);
        printWriter.flush();
    }
}
