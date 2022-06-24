package com.jeeplus.core.errors;

import com.jeeplus.aop.demo.exception.DemoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.Objects;

@ControllerAdvice
public class ExceptionTranslator {

    private final Logger logger = LoggerFactory.getLogger(ExceptionTranslator.class);
    /**
     * 处理未知异常
     */
    @ExceptionHandler({Throwable.class, java.sql.SQLException.class})
    public ResponseEntity handleException(Throwable e){

        String errMsg = "内部错误，操作异常: " + e.getMessage();
        logger.error ("{}", e);
        return new ResponseEntity(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 演示模式，禁止操作
     */
    @ExceptionHandler({DemoException.class})
    public ResponseEntity handleDemoException(Throwable e){

        String errMsg = e.getMessage();
        logger.error ("{}", e);
        return new ResponseEntity(errMsg, HttpStatus.FORBIDDEN);
    }


    /**
     * 没有访问资源的权限
     */
    @ExceptionHandler( AccessDeniedException.class)
    public ResponseEntity handleUnauthorizedException(Throwable e){

        String errMsg = "权限不足:" + e.getMessage();
        logger.error ("{}", e);
        return new ResponseEntity(errMsg, HttpStatus.FORBIDDEN);
    }

    /**
     * 登录过期，或者用户名密码错误
     * @param e
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity handleAuthenticationException(Throwable e){

        String errMsg = "认证失败: " + e.getMessage();
        logger.error ("{}", e);
        return new ResponseEntity(errMsg, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 请求参数非法
     */
    @ExceptionHandler({
            MissingServletRequestPartException.class,
            MissingServletRequestParameterException.class})
    public ResponseEntity handleBadRequestException(Throwable e){

        String errMsg = "您提交的参数，服务器无法处理";
        logger.error ("{}", e);
        return new ResponseEntity(errMsg, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理所有接口数据验证异常
     */
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ResponseEntity handleMethodArgumentNotValidException(BindException e){
        // 打印堆栈信息
        ObjectError error = e.getBindingResult().getAllErrors().get(0);
        String[] str = Objects.requireNonNull(error.getCodes())[1].split("\\.");
        String errMsg ="参数"+str[1]+", 数据验证失败:" + error.getDefaultMessage();
        logger.error ("{}", e);
        return new ResponseEntity(errMsg, HttpStatus.BAD_REQUEST);
    }

}
