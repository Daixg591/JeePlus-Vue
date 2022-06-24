package com.jeeplus.common.config;

import com.jeeplus.flowable.common.factory.MyActivityBehaviorFactory;
import com.jeeplus.flowable.service.ext.FlowIdentityServiceImpl;
import org.flowable.idm.engine.IdmEngineConfiguration;
import org.flowable.idm.engine.configurator.IdmEngineConfigurator;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.flowable.spring.boot.FlowableProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(FlowableProperties.class)
public class FlowDataSourceProcessEngineAutoConfiguration  implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {
    /**
     * @author : liugafeng
     * @date : 2021-07-11 08:50
     * <p>
     * 设置流程图识别中文
     */
    @Override
    public void configure(SpringProcessEngineConfiguration engineConfiguration) {
        engineConfiguration.setActivityFontName("宋体");
        engineConfiguration.setLabelFontName("宋体");
        engineConfiguration.setAnnotationFontName("宋体");
//        engineConfiguration.setDatabaseSchemaUpdate("true");
        engineConfiguration.setActivityBehaviorFactory(new MyActivityBehaviorFactory ());
    }




    @Bean
    public IdmEngineConfigurator idmEngineConfigurator(DataSource dataSource) {
        IdmEngineConfiguration idmEngineConfiguration = new IdmEngineConfiguration();
        idmEngineConfiguration.setDataSource(dataSource);
        idmEngineConfiguration.setIdmIdentityService(new FlowIdentityServiceImpl());

        IdmEngineConfigurator idmEngineConfigurator = new IdmEngineConfigurator();
        idmEngineConfigurator.setIdmEngineConfiguration(idmEngineConfiguration);
        return idmEngineConfigurator;
    }

}
