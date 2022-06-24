/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import com.jeeplus.common.converter.StringToDateConverter;
import com.jeeplus.common.converter.mapper.JsonMapper;
import com.jeeplus.flowable.interceptor.FlowableHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * Created by jeeplus on 2021/9/28.
 */
@EnableWebMvc
@EnableCaching //开启缓存
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private DispatcherServlet dispatcherServlet;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/act/**").addResourceLocations("classpath:/act/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FlowableHandlerInterceptor ())
                    .addPathPatterns("/app/**");
    }


    @Bean
    public ServletRegistrationBean apiServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(dispatcherServlet);
        //注入上传配置到自己注册的ServletRegistrationBean
        bean.addUrlMappings("/service/*");
        bean.setName("ModelRestServlet");
        return bean;
    }
    @Bean
    public ServletRegistrationBean restServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(dispatcherServlet);
        //注入上传配置到自己注册的ServletRegistrationBean
        bean.addUrlMappings("/rest/*");
        bean.setName("RestServlet");
        return bean;
    }

    /**
     * 配置后台返回数据的类型转换
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        List<MediaType> supportedMediaTypes = Lists.newArrayList();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);

        FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        converters.add(formHttpMessageConverter);



        StringHttpMessageConverter stringHttpMessageConverter =new StringHttpMessageConverter();
        stringHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
        converters.add(stringHttpMessageConverter);


        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setPrettyPrint(false);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
        mappingJackson2HttpMessageConverter.setObjectMapper(new JsonMapper ());
        converters.add(mappingJackson2HttpMessageConverter);

        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        List<MediaType> byteSupportedMediaTypes = Lists.newArrayList();
        byteSupportedMediaTypes.add(MediaType.ALL);
        byteSupportedMediaTypes.add(MediaType.IMAGE_PNG);
        byteSupportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        byteSupportedMediaTypes.add(MediaType.IMAGE_GIF);
        byteSupportedMediaTypes.add(MediaType.IMAGE_JPEG);
        byteSupportedMediaTypes.add(MediaType.valueOf("image/*"));
        byteArrayHttpMessageConverter.setSupportedMediaTypes(byteSupportedMediaTypes);
        converters.add(byteArrayHttpMessageConverter);


    }

    /**
     * 配置接收前台参数的类型转换
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter ( new StringToDateConverter ( ) );

    }

        /**
         * druidServlet注册
         */
    @Bean
    public ServletRegistrationBean druidServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new StatViewServlet());
        registration.addUrlMappings("/druid/*");
        return registration;
    }



}
