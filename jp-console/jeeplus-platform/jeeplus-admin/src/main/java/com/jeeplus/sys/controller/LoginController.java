/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.common.utils.ResponseUtil;
import com.jeeplus.config.properties.JeePlusProperties;
import com.jeeplus.core.errors.ErrorConstants;
import com.jeeplus.security.jwt.TokenProvider;
import com.jeeplus.security.util.SecurityUtils;
import com.jeeplus.sys.constant.CommonConstants;
import com.jeeplus.sys.constant.enums.LogTypeEnum;
import com.jeeplus.sys.service.UserService;
import com.jeeplus.sys.model.LoginForm;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录Controller
 *
 * @author jeeplus
 * @version 2021-5-31
 */
@Slf4j
@RestController
@Api(tags = "登录管理")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param loginForm
     * @param session
     * @return
     */
    @PostMapping("/sys/login")
    @ApiLog(value = "用户登录", type = LogTypeEnum.LOGIN)
    @ApiOperation("登录接口")
    @ApiModelProperty("username")
    public ResponseEntity login(@RequestBody LoginForm loginForm, HttpSession session) {
        ResponseUtil responseUtil = new ResponseUtil ( );
        String username = loginForm.getUsername ();
        String password = loginForm.getPassword ();
        String code = loginForm.getCode ();
        if(!code.equals ( session.getAttribute ( "code" ) )){
            throw new AccountExpiredException ( ErrorConstants.LOGIN_ERROR_ERROR_VALIDATE_CODE );
        }
        SecurityUtils.login (username, password, authenticationManager  ); //登录操作
        //登录成功，生成token
        UserDTO userDTO = UserUtils.getByLoginName ( username );
        responseUtil.add ( TokenProvider.TOKEN, TokenProvider.createAccessToken ( username, userDTO.getPassword () ) );
        responseUtil.add ( TokenProvider.REFRESH_TOKEN, TokenProvider.createRefreshToken ( username, userDTO.getPassword ( ) ) );
        responseUtil.add ( "oldLoginDate", DateUtil.format (userDTO.getLoginDate () , "yyyy-MM-dd HH:mm:ss"));
        responseUtil.add ( "oldLoginIp", userDTO.getLoginIp () );
        //更新登录日期
        userService.updateUserLoginInfo ( userDTO );
        return responseUtil.ok ( );
    }


    /**
     * cas登录
     * vue 传递ticket参数验证，并返回token
     */
    @ApiLog(value = "单点登录", type = LogTypeEnum.ACCESS)
    @RequestMapping("/sys/casLogin")
    public ResponseEntity casLogin(@RequestParam(name = "ticket") String ticket,
                                   @RequestParam(name = "service") String service, @Value("${cas.server-url-prefix}") String casServer) throws Exception {
        //ticket检验器
        TicketValidator ticketValidator = new Cas20ServiceTicketValidator ( casServer );
        try {
            // 去CAS服务端中验证ticket的合法性
            Assertion casAssertion = ticketValidator.validate ( ticket, service );
            // 从CAS服务端中获取相关属性,包括用户名、是否设置RememberMe等
            AttributePrincipal casPrincipal = casAssertion.getPrincipal ( );
            String loginName = casPrincipal.getName ( );
            // 校验用户名密码
            UserDTO userDTO = UserUtils.getByLoginName ( loginName );
            if ( userDTO != null ) {
                if ( CommonConstants.NO.equals ( userDTO.getLoginFlag ( ) ) ) {
                    throw new LockedException ( ErrorConstants.LOGIN_ERROR_FORBIDDEN );
                }
                // 单点登录实现不需要校验用户名密码
//                SecurityUtils.login (userDTO.getLoginName (), userDTO.getPassword (), authenticationManager  );
                String token = TokenProvider.createAccessToken ( userDTO.getLoginName (), userDTO.getPassword () );
                Authentication authentication = TokenProvider.getAuthentication ( token );
//                authenticationManager.authenticate(authentication); 验证不需要
                SecurityContextHolder.getContext ( ).setAuthentication ( authentication );

                return ResponseUtil.newInstance ( ).add ( TokenProvider.TOKEN, TokenProvider.createAccessToken (userDTO.getLoginName (), userDTO.getPassword ()) )
                        .add ( TokenProvider.REFRESH_TOKEN, TokenProvider.createRefreshToken ( userDTO.getLoginName ( ), userDTO.getPassword ( ) ) ).ok ( );
            } else {
                AuthenticationException e = new UsernameNotFoundException ( ErrorConstants.LOGIN_ERROR_NOTFOUND );
                log.error ( "用户【loginName:" + loginName + "】不存在!", e );
                throw e;
            }
        } catch (TicketValidationException e) {
            log.error ( "Unable to validate ticket [" + ticket + "]", e );
            throw new CredentialsExpiredException ( "未通过验证的ticket [" + ticket + "]", e );
        }
    }

    /**
     * 刷新token
     * @param refreshToken
     * @return
     */
    @ApiLog("刷新token")
    @GetMapping("/sys/refreshToken")
    @ApiOperation("刷新token")
    public ResponseEntity accessTokenRefresh(String refreshToken) {
        // 验证token是否合法
        if(!TokenProvider.validateToken ( refreshToken )){
            return ResponseEntity.status ( HttpStatus.UNAUTHORIZED ).body ( ErrorConstants.LOGIN_ERROR_EXPIRED );
        }
        String loginName = TokenProvider.getLoginName ( refreshToken );
        String password = UserUtils.getByLoginName ( loginName ).getPassword ( );
        //创建新的accessToken
        String accessToken = TokenProvider.createAccessToken ( loginName, password);

        //下面判断是否刷新 REFRESH_TOKEN，如果refreshToken 快过期了 需要重新生成一个替换掉
        long minTimeOfRefreshToken = 2 * JeePlusProperties.newInstance ( ).getEXPIRE_TIME ( );//REFRESH_TOKEN 有效时长是应该为accessToken有效时长的2倍
        Long refreshTokenExpirationTime = JWT.decode ( refreshToken ).getExpiresAt ( ).getTime ( );//refreshToken创建的起始时间点
        //(refreshToken过期时间- 当前时间点) 表示refreshToken还剩余的有效时长，如果小于2倍accessToken时长 ，则刷新 REFRESH_TOKEN
        if ( refreshTokenExpirationTime - System.currentTimeMillis ( ) <= minTimeOfRefreshToken ) {
            //刷新refreshToken
            refreshToken = TokenProvider.createRefreshToken ( loginName, password );
        }

        return ResponseUtil.newInstance ( ).add ( TokenProvider.TOKEN, accessToken ).add ( TokenProvider.REFRESH_TOKEN, refreshToken ).ok ( );
    }


    /**
     * 退出登录
     * @param request
     * @param response
     * @return
     */
    @ApiOperation("退出登录")
    @ApiLog(value = "退出登录", type = LogTypeEnum.LOGIN)
    @GetMapping("/sys/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityUtils.getAuthentication ();
        if ( auth != null ) {
            UserUtils.deleteCache (UserUtils.getCurrentUserDTO () );
            new SecurityContextLogoutHandler ( ).logout ( request, response, auth );
        }
        return ResponseEntity.ok ( "退出成功" );
    }


    /**
     * 获取登陆验证码
     * @param response
     * @param session
     * @throws
     */
    @ApiOperation ("获取验证码")
    @ApiLog("获取验证码")
    @GetMapping("/sys/getCode")
    public void getCode(HttpServletResponse response, HttpSession session){
        //HuTool定义图形验证码的长和宽,验证码的位数，干扰线的条数
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(116, 36,4,50);
        //将验证码放入session
        session.setAttribute("code",lineCaptcha.getCode());
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            lineCaptcha.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            log.error ("{}", e );
        }
    }


}
