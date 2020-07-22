package com.spring.cloud.ms.sleuth.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin2.server.internal.EnableZipkinServer;

@SpringBootApplication
// 驱动Zipkin服务器
@EnableZipkinServer
public class MsSleuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsSleuthApplication.class, args);
    }

}
