package com.lp.test.mybatisplusdemo.modules.test.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lp
 * @since 2020-12-12 16:58:35
 */
@Configuration
@MapperScan("com.lp.test.mybatisplusdemo.modules.test.mapper")
public class Myconfig {
    /**
     * 分页插件
     * @return 分页插件的实例
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
