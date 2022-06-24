package com.jeeplus.aop.demo.config;

import com.jeeplus.aop.demo.DemoAspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
public class DemoAspectConfiguration {
    @Value ( "${demoMode}" )
    public boolean demoMode;

    @Bean
    public DemoAspect demoAspect(Environment env) {
        return new DemoAspect (env, demoMode);
    }
}
