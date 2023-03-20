package com.dsj.application.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置分页查询的拦截器*/
@Configuration
public class MpConfig {
    // 创建mybayis-plus拦截器的Bean
    @Bean
    public MybatisPlusInterceptor mpInterceptor(){
        // 创建mybatis-plus的拦截器
        MybatisPlusInterceptor mpInterceptor = new MybatisPlusInterceptor();
        // 添加具体的拦截器
        mpInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mpInterceptor;
    }
}
