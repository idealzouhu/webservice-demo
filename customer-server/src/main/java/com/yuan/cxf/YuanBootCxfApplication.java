package com.yuan.cxf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@MapperScan("com.yuan.cxf.dao")
public class YuanBootCxfApplication {
    public static void main(String[] args) {
        SpringApplication.run(YuanBootCxfApplication.class,args);
    }
}
