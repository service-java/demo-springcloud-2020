package com.spring.cloud.sleuth.provider.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages ="com.spring.cloud.sleuth.provider")
public class SleuthProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SleuthProviderApplication.class, args);
    }

}
