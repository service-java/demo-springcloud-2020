package com.spring.cloud.mszuul.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**** imports ****/
@SpringBootApplication(scanBasePackages = "com.spring.cloud.mszuul")
// 驱动Zuul工作
@EnableZuulProxy
@EnableRedisHttpSession
public class MsZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsZuulApplication.class, args);
    }
}
