package com.spring.cloud.sleuth.zuul.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication(scanBasePackages="com.spring.cloud.sleuth.zuul")
@EnableZuulProxy // 驱动Zuul网关服务
public class SleuthZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SleuthZuulApplication.class, args);
    }

}
