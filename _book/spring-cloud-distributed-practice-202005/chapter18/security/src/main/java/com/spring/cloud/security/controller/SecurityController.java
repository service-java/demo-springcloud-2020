package com.spring.cloud.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**** imports ****/
@RestController
public class SecurityController {

    // “USER”和“ADMIN”角色都可以访问
    @GetMapping("/user/test")
    public String userTest() {
        return "user-test";
    }

    // 只有“ADMIN”角色可以访问
    @GetMapping("/admin/test")
    public String adminTest() {
        return "admin-test";
    }
}