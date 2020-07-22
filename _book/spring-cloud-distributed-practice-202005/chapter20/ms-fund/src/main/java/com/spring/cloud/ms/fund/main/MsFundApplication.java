package com.spring.cloud.ms.fund.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.spring.cloud.ms.fund")
@EnableFeignClients(basePackages = "com.spring.cloud.ms.fund")
public class MsFundApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsFundApplication.class, args);
    }

}
