package com.jeeplus.config;

import com.jeeplus.aop.logging.LoggingAspect;
import com.jeeplus.aop.logging.service.AsyncLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {
    @Autowired
    AsyncLogService asyncLogService;

    @Bean
    public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env, asyncLogService );
    }
}
