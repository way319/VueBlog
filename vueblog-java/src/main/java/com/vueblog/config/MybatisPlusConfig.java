package com.vueblog.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author m
 * @version 1.0
 * @className MybatisPlusConfig
 * @description MybatisPlusConfig
 * @date 2021/1/31
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.vueblog.mapper")
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
