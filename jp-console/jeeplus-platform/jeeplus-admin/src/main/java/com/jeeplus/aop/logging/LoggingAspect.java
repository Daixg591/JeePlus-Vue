package com.jeeplus.aop.logging;

import com.jeeplus.aop.logging.service.AsyncLogService;
import com.jeeplus.aop.logging.utils.LogUtils;
import com.jeeplus.sys.domain.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.env.Environment;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 日志切面类
 */
@Aspect
public class LoggingAspect {

    private final Environment env;

    private final AsyncLogService asyncLogService;


    public LoggingAspect(Environment env, AsyncLogService asyncLogService) {
        this.env = env;
        this.asyncLogService = asyncLogService;
    }

    public HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull ( RequestContextHolder.getRequestAttributes ( ) )).getRequest ( );
    }


    /**
     * 切点，扫描ApiLog注解的类
     */
    @Pointcut("@annotation(com.jeeplus.aop.logging.annotation.ApiLog)")
    public void applicationPackagePointcut() {
        // 方法为空，因为这只是一个切入点，实现在advices.
    }

    /**
     * 记录异常日志
     * @param joinPoint
     * @param e
     * @throws Throwable
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable {

        HttpServletRequest request = getHttpServletRequest ( );
        Log log = LogUtils.getLog ( request, joinPoint, 0, null, e );
        asyncLogService.saveLog ( log );

    }

    /**
     * 在进入和退出方法时记录日志
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        /**
         * 登录前和退出后中，获取不到currentUser信息，所以在这里做特殊处理
         */
        long startTime = System.currentTimeMillis ( );
        Object result = joinPoint.proceed ( );
        long time = System.currentTimeMillis ( ) - startTime;
        HttpServletRequest request = getHttpServletRequest ( );
        Log log = LogUtils.getLog ( request, joinPoint, time, result != null ? result.toString ( ) : "", null );
        asyncLogService.saveLog ( log );
        return result;
    }
}
