package com.jeeplus.security.config;

import com.jeeplus.security.exception.JwtAccessDeniedHandler;
import com.jeeplus.security.exception.JwtAuthenticationEntryPoint;
import com.jeeplus.security.jwt.JWTConfigurer;
import com.jeeplus.security.jwt.TokenProvider;
import com.jeeplus.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启方法级安全验证
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService userDatailService;
    @Autowired
    private  JwtAuthenticationEntryPoint unauthorizedHandler;
    @Autowired
    private JwtAccessDeniedHandler accessDeniedHandler;


    /**
     * 描述：设置授权处理相关的具体类以及加密方式
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // 设置不隐藏 未找到用户异常
        provider.setHideUserNotFoundExceptions(true);
        // 用户认证service - 查询数据库的逻辑
        provider.setUserDetailsService(userDetailsService());
        // 设置密码加密算法
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService ( userDatailService );
        auth.authenticationProvider(provider);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).and()
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers ( "/401"
                        ,"/404",
                        "/app/rest/**",
                        "/druid/**",
                        "/v1/**",
                        "/v2/**",
                        "/weboffice/**",
                        "/ReportServer/**",
                        "/sys/login",
                        "/app/sys/login",
                        "/sys/refreshToken/**",
                        "/sys/sysConfig/getConfig",
                        "/sys/getCode",
                        "/sys/casLogin").permitAll() // 允许请求无需认证
                .antMatchers( HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated() // 所有请求都需要验证
                .and ()

                .apply(securityConfigurerAdapter())
         ;
    }

    /**
     * 描述: 静态资源放行，这里的放行，是不走 Spring Security 过滤器链
     **/
    @Override
    public void configure(WebSecurity web) {
        // 可以直接访问的静态数据
        web.ignoring()
                .antMatchers(
                        "/doc.html",
                        "/swagger-ui.html",
                        "/userfiles/**",
                        "/swagger**/**",
                        "/webjars/**");
    }

    /**
     * 描述: 密码加密算法 BCrypt 推荐使用
     **/
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 描述: 注入token
     **/
    @Bean
    public TokenProvider tokenProvider() {
        return new TokenProvider ();
    }

    private JWTConfigurer securityConfigurerAdapter() throws Exception{
        return new JWTConfigurer(tokenProvider () , authenticationManager ());
    }

    /**
     * 描述: 注入AuthenticationManager管理器
     **/
    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
