package com.spring.cloud.sso.client2.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

/**** imports ****/
@SpringBootApplication
// 驱动应用为SSO客户端（使用的是OAuth2.0协议）
@EnableOAuth2Sso
public class SsoClient2Application {
    public static void main(String[] args) {
        SpringApplication.run(SsoClient2Application.class, args);
    }
}