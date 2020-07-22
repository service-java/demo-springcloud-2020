package com.spring.cloud.ms.product.database;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DataSourcesContentHolder {
    // 线程副本
    private static final ThreadLocal<Long> contextHolder = new ThreadLocal<>();

    // 设置id
    public static void setId(Long id) {
        contextHolder.set(id);
    }

    // 获取线程id
    public static Long getId() {
        return contextHolder.get();
    }

    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String userPwd = encoder.encode("user123");
        System.out.println(userPwd);
        String adminPwd = encoder.encode("admin123");
        System.out.println(adminPwd);

    }
}