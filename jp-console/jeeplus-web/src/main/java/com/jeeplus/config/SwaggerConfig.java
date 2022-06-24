package com.jeeplus.config;

import com.google.common.base.Predicates;
import com.jeeplus.security.jwt.TokenProvider;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
public class SwaggerConfig {



    /**
     * swagger2的配置文件
     * @return Docket
     */
    @Bean
    public Docket createRestApi()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("系统管理")
                .apiInfo(apiInfo())
                .select()
                //此包路径下的类，才生成接口文档
                .apis(Predicates.or(
                        RequestHandlerSelectors.basePackage("com.jeeplus"))
                )
                //加了ApiOperation注解的类，才生成接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(securityScheme()));
    }



    /***
     * swagger token 配置。
     */
    @Bean
    SecurityScheme securityScheme() {
        return new ApiKey( TokenProvider.TOKEN, TokenProvider.TOKEN, "header");
    }


    /**
     * api文档的详细信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // //大标题
                .title("Jeeplus 微服务接口文档")
                // 版本号
                .version("9.0")
                // 描述
                .description("API接口")
                // 作者
                .contact(new Contact("jeeplus", "http://www.jeeplus.org", "117575171@qq.com"))
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }

}
