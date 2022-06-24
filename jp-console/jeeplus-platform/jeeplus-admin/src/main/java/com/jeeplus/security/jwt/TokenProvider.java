package com.jeeplus.security.jwt;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jeeplus.config.properties.JeePlusProperties;
import com.jeeplus.core.errors.ErrorConstants;
import com.jeeplus.sys.service.dto.MenuDTO;
import com.jeeplus.sys.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class TokenProvider {
    public static final String TOKEN = "token";

    public static final String REFRESH_TOKEN = "refreshToken";

    /**
     * 校验token是否正确
     * @param token 密钥
     * @return 是否正确
     */
    public static boolean validateToken(String token) {
        try {
            String userName = TokenProvider.getLoginName(token);
            String password = UserUtils.getByLoginName(userName).getPassword();
            Algorithm algorithm = Algorithm.HMAC256(password);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", userName)
                    .build();
            verifier.verify(token);
            return true;
        } catch (TokenExpiredException e){
            log.info (ErrorConstants.LOGIN_ERROR_EXPIRED);
        }
        catch (Exception e) {
            log.info(ErrorConstants.LOGIN_ERROR_INCORRECT);
        }
        return false;

    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getLoginName(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 生成签名
     * @param username 用户名
     * @param password 用户的密码
     * @return 加密的token
     */
    public static String createAccessToken(String username, String password) {
        Date date = new Date(System.currentTimeMillis() + JeePlusProperties.newInstance().getEXPIRE_TIME());
        Algorithm algorithm = Algorithm.HMAC256(password);
        // 附带username信息
        return JWT.create()
                .withClaim("username", username)
                .withExpiresAt(date)
                .sign(algorithm);
    }


    public static Authentication getAuthentication(String token) {
        DecodedJWT jwt = JWT.decode(token);
        String username =  jwt.getClaim("username").asString();

        // 权限集合
        List <GrantedAuthority> authorities = new ArrayList <> ();
        // 得到用户角色
        List<MenuDTO> list = UserUtils.getMenuDTOListByUser ( UserUtils.getByLoginName ( username ) );
        for (MenuDTO menuDTO : list){
            if ( StringUtils.isNotBlank(menuDTO.getPermission())){
                // 添加基于Permission的权限信息
                for (String permission : StringUtils.split(menuDTO.getPermission(),",")){
                    authorities.add(new SimpleGrantedAuthority (permission));
                }
            }
        }

        User principal = new User (username, "", authorities);

        return new UsernamePasswordAuthenticationToken (principal, token, authorities);
    }

    /**
     * refresh TOKEN 刷新用
     * @param username 用户名
     * @param password 用户的密码
     * @return 加密的token
     */
    public static String createRefreshToken(String username, String password) {
        Date date = new Date(System.currentTimeMillis() + 3* JeePlusProperties.newInstance().getEXPIRE_TIME());
        Algorithm algorithm = Algorithm.HMAC256(password);
        // 附带username信息
        return JWT.create()
                .withClaim("username", username)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 获取token，支持三种方式, 请求参数、header、cookie， 优先级依次降低，以请求参数中的优先级最高。
     * @param httpServletRequest
     * @return
     */
    public static String resolveToken(HttpServletRequest httpServletRequest){
        if(httpServletRequest == null){
            return  null;
        }
        String token0 = httpServletRequest.getParameter( TokenProvider.TOKEN);
        String token1 = httpServletRequest.getHeader( TokenProvider.TOKEN);
        Cookie token2 =  ServletUtil.getCookie (httpServletRequest, TokenProvider.TOKEN);
        if( StrUtil.isNotBlank(token0)){
            return token0;
        }
        if(StrUtil.isNotBlank(token1)){
            return token1;
        }
        if(token2 !=null && StrUtil.isNotBlank(token2.getValue ())){
            return token2.getValue ();
        }
        return null;
    }

}
