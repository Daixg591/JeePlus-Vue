package com.jeeplus.ureport;

import com.bstek.ureport.console.UReportServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@ImportResource("classpath:context.xml")
@Configuration
public class ReportConfiguration {


    @Bean
    public ServletRegistrationBean<UReportServlet> servletRegistrationBean() {
        return new ServletRegistrationBean<>(new UReportServlet(), "/ureport/*");
    }

}
