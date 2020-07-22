package com.spring.cloud.ms.admin.main;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 驱动Admin服务端启动
@EnableAdminServer
public class MsAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsAdminApplication.class, args);
    }

}
