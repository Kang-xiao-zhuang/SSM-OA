package com.zhuang.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * description: ServiceAuthApplication
 * date: 2023/3/1 14:27
 * author: Zhuang
 * version: 1.0
 */
@SpringBootApplication
@ComponentScan("com.zhuang")
@MapperScan("com.zhuang.*.mapper")
public class ServiceAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthApplication.class, args);
    }

}
