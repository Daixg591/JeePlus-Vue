package com.jeeplus.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.jeeplus.**.mapper.**")
public class MybatisPlusConfig {

    @Bean
    public MySqlInjector sqlInjector() {
        return new MySqlInjector();
    }


    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加自定义的数据权限处理器
        interceptor.addInnerInterceptor(new DataPermissionInterceptor(new DataPermissionHandlerImpl()));
        // 添加分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor ();
        paginationInnerInterceptor.setOverflow ( true );
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }



    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
//            configuration.setUseDeprecatedExecutor(false);
            configuration.setJdbcTypeForNull (JdbcType.NULL);
        };
    }
}
