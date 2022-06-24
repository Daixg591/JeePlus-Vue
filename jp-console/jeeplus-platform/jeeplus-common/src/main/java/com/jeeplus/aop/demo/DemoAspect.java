package com.jeeplus.aop.demo;

import com.jeeplus.aop.demo.annotation.DemoMode;
import com.jeeplus.aop.demo.exception.DemoException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 日志切面类
 */
@Aspect
public class DemoAspect {

    private final Environment env;

    private final boolean demoMode;


    public DemoAspect(Environment env, boolean demoMode) {
        this.env = env;
        this.demoMode = demoMode;
    }

    public HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull ( RequestContextHolder.getRequestAttributes ( ) )).getRequest ( );
    }


    /**
     * 切点，扫描所有录下的controller类
     */
    @Pointcut("within(com.jeeplus..controller..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Retrieves the {@link Logger} associated to the given {@link JoinPoint}.
     *
     * @param joinPoint join point we want the logger for.
     * @return {@link Logger} associated to the given {@link JoinPoint}.
     */
    private Logger logger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger ( joinPoint.getSignature ( ).getDeclaringTypeName ( ) );
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice.
     */
    @Before( "applicationPackagePointcut()")
    public void logAfterThrowing(JoinPoint joinPoint) throws Throwable{


        MethodSignature signature = (MethodSignature) joinPoint.getSignature ( );
        Method method = signature.getMethod ( );

        // 演示模式
        if(this.demoMode &&  method.getAnnotation ( DemoMode.class ) != null){
            throw new DemoException ("演示模式，禁止此操作!");
        }


    }


}
