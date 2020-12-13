package com.lp.test.mybatisplusdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan(basePackages ={"com.lp.test.mybatisplusdemo.poi.service.mapper","com.lp.test.mybatisplusdemo.modules.test.service.mapper"})
@SpringBootApplication
public class MybatisPlusDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusDemoApplication.class, args);
    }

}
