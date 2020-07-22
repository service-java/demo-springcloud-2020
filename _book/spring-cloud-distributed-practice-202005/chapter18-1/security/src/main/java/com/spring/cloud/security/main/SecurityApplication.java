package com.spring.cloud.security.main;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**** imports ****/
// 设置扫描包
@SpringBootApplication( scanBasePackages = "com.spring.cloud.security")
// MyBatis扫描路径
@MapperScan(basePackages = "com.spring.cloud.security",
        annotationClass = Mapper.class)
@EnableRedisHttpSession
public class SecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
}