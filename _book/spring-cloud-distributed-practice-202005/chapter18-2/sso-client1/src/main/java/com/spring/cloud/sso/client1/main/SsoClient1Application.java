package com.spring.cloud.sso.client1.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**** imports ****/

@SpringBootApplication
@RestController
// 驱动应用为SSO客户端（使用的是OAuth2.0协议）
@EnableOAuth2Sso
public class SsoClient1Application extends WebSecurityConfigurerAdapter { // ①

    // 展示用户权限信息
    @GetMapping("/auth/info")
    public Authentication authInfo(Authentication user) { 
        return user;
    }

    // 角色“USER”权限测试
    @GetMapping("/user/test")
    public String userTest() { // ②
        return "user test";
    }

    // 角色“ADMIN”权限测试
    @GetMapping("/admin/test")
    public String adminTest() { // ③
        return "admin test";
    }

    // 配置应用权限
    @Override
    protected void configure(HttpSecurity http) throws Exception { // ④
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/auth/**").hasAnyRole("USER", "ADMIN")
            .antMatchers("/user/**").hasAnyRole("USER")
            .antMatchers("/admin/**").hasAnyRole("ADMIN")
            .anyRequest().permitAll()
            .and().formLogin();
    }

    public static void main(String[] args) {
        SpringApplication.run(SsoClient1Application.class, args);
    }
}