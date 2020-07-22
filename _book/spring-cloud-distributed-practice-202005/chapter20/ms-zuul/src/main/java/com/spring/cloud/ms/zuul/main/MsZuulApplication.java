package com.spring.cloud.ms.zuul.main;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

// 标注Spring Boot工程
@SpringBootApplication(scanBasePackages = "com.spring.cloud.ms.zuul")
// 使用spring-session-data-redis，这样多个实例就可以共享缓存了
@EnableRedisHttpSession
// 驱动Zuul工作
@EnableZuulProxy
// MyBatis映射器扫描
@MapperScan(basePackages = "com.spring.cloud.ms.zuul", annotationClass = Mapper.class)
// 启用Spring缓存机制
@EnableCaching
public class MsZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsZuulApplication.class, args);
    }

}
