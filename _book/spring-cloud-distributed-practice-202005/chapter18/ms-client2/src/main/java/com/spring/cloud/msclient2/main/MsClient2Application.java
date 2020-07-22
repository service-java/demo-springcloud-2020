package com.spring.cloud.msclient2.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.spring.cloud.msclient2")
public class MsClient2Application {
    public static void main(String[] args) {
        SpringApplication.run(MsClient2Application.class, args);
    }
}
