package com.spring.cloud.cfgclient.main;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController // REST风格控制器
public class CfgClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(CfgClientApplication.class, args);
    }

    // 读取配置文件的信息
    @Value("${version.message}") // ①
    private String versionMsg = null;

    // 展示配置文件信息
    @GetMapping("/version/message")
    public  String versionMessage() {
        return versionMsg;
    }
}
