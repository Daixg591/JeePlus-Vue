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
 * ??????Controller
 *
 * @author jeeplus
 * @version 2021-5-31
 */
@Slf4j
@RestController
@Api(tags = "????????????")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    /**
     * ????????????
     * @param loginForm
     * @param session
     * @return
     */
    @PostMapping("/sys/login")
    @ApiLog(value = "????????????", type = LogTypeEnum.LOGIN)
    @ApiOperation("????????????")
    @ApiModelProperty("username")
    public ResponseEntity login(@RequestBody LoginForm loginForm, HttpSession session) {
        ResponseUtil responseUtil = new ResponseUtil ( );
        String username = loginForm.getUsername ();
        String password = loginForm.getPassword ();
        String code = loginForm.getCode ();
        if(!code.equals ( session.getAttribute ( "code" ) )){
            throw new AccountExpiredException ( ErrorConstants.LOGIN_ERROR_ERROR_VALIDATE_CODE );
        }
        SecurityUtils.login (username, password, authenticationManager  ); //????????????
        //?????????????????????token
        UserDTO userDTO = UserUtils.getByLoginName ( username );
        responseUtil.add ( TokenProvider.TOKEN, TokenProvider.createAccessToken ( username, userDTO.getPassword () ) );
        responseUtil.add ( TokenProvider.REFRESH_TOKEN, TokenProvider.createRefreshToken ( username, userDTO.getPassword ( ) ) );
        responseUtil.add ( "oldLoginDate", DateUtil.format (userDTO.getLoginDate () , "yyyy-MM-dd HH:mm:ss"));
        responseUtil.add ( "oldLoginIp", userDTO.getLoginIp () );
        //??????????????????
        userService.updateUserLoginInfo ( userDTO );
        return responseUtil.ok ( );
    }


    /**
     * cas??????
     * vue ??????ticket????????????????????????token
     */
    @ApiLog(value = "????????????", type = LogTypeEnum.ACCESS)
    @RequestMapping("/sys/casLogin")
    public ResponseEntity casLogin(@RequestParam(name = "ticket") String ticket,
                                   @RequestParam(name = "service") String service, @Value("${cas.server-url-prefix}") String casServer) throws Exception {
        //ticket?????????
        TicketValidator ticketValidator = new Cas20ServiceTicketValidator ( casServer );
        try {
            // ???CAS??????????????????ticket????????????
            Assertion casAssertion = ticketValidator.validate ( ticket, service );
            // ???CAS??????????????????????????????,??????????????????????????????RememberMe???
            AttributePrincipal casPrincipal = casAssertion.getPrincipal ( );
            String loginName = casPrincipal.getName ( );
            // ?????????????????????
            UserDTO userDTO = UserUtils.getByLoginName ( loginName );
            if ( userDTO != null ) {
                if ( CommonConstants.NO.equals ( userDTO.getLoginFlag ( ) ) ) {
                    throw new LockedException ( ErrorConstants.LOGIN_ERROR_FORBIDDEN );
                }
                // ????????????????????????????????????????????????
//                SecurityUtils.login (userDTO.getLoginName (), userDTO.getPassword (), authenticationManager  );
                String token = TokenProvider.createAccessToken ( userDTO.getLoginName (), userDTO.getPassword () );
                Authentication authentication = TokenProvider.getAuthentication ( token );
//                authenticationManager.authenticate(authentication); ???????????????
                SecurityContextHolder.getContext ( ).setAuthentication ( authentication );

                return ResponseUtil.newInstance ( ).add ( TokenProvider.TOKEN, TokenProvider.createAccessToken (userDTO.getLoginName (), userDTO.getPassword ()) )
                        .add ( TokenProvider.REFRESH_TOKEN, TokenProvider.createRefreshToken ( userDTO.getLoginName ( ), userDTO.getPassword ( ) ) ).ok ( );
            } else {
                AuthenticationException e = new UsernameNotFoundException ( ErrorConstants.LOGIN_ERROR_NOTFOUND );
                log.error ( "?????????loginName:" + loginName + "????????????!", e );
                throw e;
            }
        } catch (TicketValidationException e) {
            log.error ( "Unable to validate ticket [" + ticket + "]", e );
            throw new CredentialsExpiredException ( "??????????????????ticket [" + ticket + "]", e );
        }
    }

    /**
     * ??????token
     * @param refreshToken
     * @return
     */
    @ApiLog("??????token")
    @GetMapping("/sys/refreshToken")
    @ApiOperation("??????token")
    public ResponseEntity accessTokenRefresh(String refreshToken) {
        // ??????token????????????
        if(!TokenProvider.validateToken ( refreshToken )){
            return ResponseEntity.status ( HttpStatus.UNAUTHORIZED ).body ( ErrorConstants.LOGIN_ERROR_EXPIRED );
        }
        String loginName = TokenProvider.getLoginName ( refreshToken );
        String password = UserUtils.getByLoginName ( loginName ).getPassword ( );
        //????????????accessToken
        String accessToken = TokenProvider.createAccessToken ( loginName, password);

        //???????????????????????? REFRESH_TOKEN?????????refreshToken ???????????? ?????????????????????????????????
        long minTimeOfRefreshToken = 2 * JeePlusProperties.newInstance ( ).getEXPIRE_TIME ( );//REFRESH_TOKEN ????????????????????????accessToken???????????????2???
        Long refreshTokenExpirationTime = JWT.decode ( refreshToken ).getExpiresAt ( ).getTime ( );//refreshToken????????????????????????
        //(refreshToken????????????- ???????????????) ??????refreshToken???????????????????????????????????????2???accessToken?????? ???????????? REFRESH_TOKEN
        if ( refreshTokenExpirationTime - System.currentTimeMillis ( ) <= minTimeOfRefreshToken ) {
            //??????refreshToken
            refreshToken = TokenProvider.createRefreshToken ( loginName, password );
        }

        return ResponseUtil.newInstance ( ).add ( TokenProvider.TOKEN, accessToken ).add ( TokenProvider.REFRESH_TOKEN, refreshToken ).ok ( );
    }


    /**
     * ????????????
     * @param request
     * @param response
     * @return
     */
    @ApiOperation("????????????")
    @ApiLog(value = "????????????", type = LogTypeEnum.LOGIN)
    @GetMapping("/sys/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityUtils.getAuthentication ();
        if ( auth != null ) {
            UserUtils.deleteCache (UserUtils.getCurrentUserDTO () );
            new SecurityContextLogoutHandler ( ).logout ( request, response, auth );
        }
        return ResponseEntity.ok ( "????????????" );
    }


    /**
     * ?????????????????????
     * @param response
     * @param session
     * @throws
     */
    @ApiOperation ("???????????????")
    @ApiLog("???????????????")
    @GetMapping("/sys/getCode")
    public void getCode(HttpServletResponse response, HttpSession session){
        //HuTool?????????????????????????????????,???????????????????????????????????????
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(116, 36,4,50);
        //??????????????????session
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
