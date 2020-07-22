package com.spring.cloud.sleuth.consumer.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**** imports ****/
@SpringBootApplication( // 定义扫描包
        scanBasePackages = "com.spring.cloud.sleuth.consumer")
@EnableFeignClients( //扫描装配OpenFeign接口到IoC容器中 ①
        basePackages="com.spring.cloud.sleuth.consumer")
public class SleuthConsumerApplication {


    @Bean
    @LoadBalanced // 负载均衡
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(SleuthConsumerApplication.class, args);
    }

}
