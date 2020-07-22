package com.spring.cloud.msclient1.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**** imports ****/
@SpringBootApplication(scanBasePackages = "com.spring.cloud.msclient1")
// 启用OpenFeign客户端
@EnableFeignClients(basePackages = "com.spring.cloud.msclient1")
public class MsClient1Application {

    public static void main(String[] args) {
        SpringApplication.run(MsClient1Application.class, args);
    }

}
