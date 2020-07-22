package com.spring.cloud.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PageController {

    // 页面访问
    @GetMapping("/user/csrf/form")
    public String csrfForm() {
        return "csrf-form";
    }

    // 提交路径
    @PostMapping("/user/csrf/commit")
    @ResponseBody
    public String commit() {
        return "commit";
    }

    @GetMapping("/user/visit")
    public String visitPage() {
        return "visit-page";
    }

    @GetMapping("/login/page")
    public String login() {
        return "login";
    }
}
