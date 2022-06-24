package com.jeeplus.aop.logging.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.security.jwt.TokenProvider;
import com.jeeplus.sys.constant.enums.LogTypeEnum;
import com.jeeplus.sys.domain.Log;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LogUtils {

    /**
     * 设置请求参数
     *
     * @param paramMap
     */
    public static String getRequestParams(Map paramMap) {
        Map map = new HashMap ( );
        if ( paramMap == null ) {
            return "";
        }
        for (Map.Entry <String, String[]> param : ((Map <String, String[]>) paramMap).entrySet ( )) {
            String paramValue = (param.getValue ( ) != null && param.getValue ( ).length > 0 ? param.getValue ( )[0] : "");
            map.put ( param.getKey ( ), StringUtils.abbreviate ( StringUtils.endsWithIgnoreCase ( param.getKey ( ), "password" ) ? "安全考虑，密码已隐藏" : paramValue, 100 ) );
        }
        return JSONUtil.toJsonStr ( map );
    }

    /**
     * 解析日志
     * @param request
     * @param joinPoint
     * @param recordTime
     * @param result
     * @param e
     * @return
     */
    public static Log getLog(HttpServletRequest request, JoinPoint joinPoint, long recordTime, String result, Throwable e) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature ( );
        Method method = signature.getMethod ( );
        // 注解日志
        ApiLog aopLog = method.getAnnotation ( ApiLog.class );
        String functionName = aopLog.value ( );
        LogTypeEnum logTypeEnum = aopLog.type ( );

        /**
         * 获取用户信息
         */
        String userId = null;
        String token = TokenProvider.resolveToken ( request );
        String loginName = TokenProvider.getLoginName ( token );
        if ( StrUtil.isNotEmpty ( loginName ) ) {
            UserDTO user = UserUtils.getByLoginName ( loginName );
            userId = user != null ? user.getId ( ) : null;
        }

        // 保存日志
        Log log = new Log ( );
        log.setRecordTime ( recordTime );
        log.setTitle ( functionName );
        log.setType ( logTypeEnum.getValue ( ) );
        log.setRemoteAddr ( ServletUtil.getClientIP ( request ) );
        log.setUserAgent ( request.getHeader ( "user-agent" ) );
        log.setRequestUri ( request.getRequestURI ( ) );
        log.setRequestType ( request.getMethod ( ) );
        log.setMethod ( joinPoint.getSignature ( ).toString ( ) );
        log.setParams ( getRequestParams ( request.getParameterMap ( ) ) );
        log.setResult ( result );
        log.setCreateBy ( userId );
        // 处理异常信息
        String exception = "";
        if ( e != null ) {
            StringWriter stringWriter = new StringWriter ( );
            e.printStackTrace ( new PrintWriter ( stringWriter ) );
            exception = stringWriter.toString ( );
            log.setException ( exception );
            log.setResult ( e.getMessage ( ) );
            // 非login类型日志修改未异常类型
            if ( !log.getType ( ).equals ( LogTypeEnum.LOGIN.getValue ( ) ) ) {
                log.setType ( LogTypeEnum.EXCEPTION.getValue ( ) );
            }
        }
        return log;
    }
}
