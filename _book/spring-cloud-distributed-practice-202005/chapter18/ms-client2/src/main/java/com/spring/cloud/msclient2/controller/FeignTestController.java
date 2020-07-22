package com.spring.cloud.msclient2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignTestController {

    @GetMapping("/feign/test")
    public String feign() {
        return "test";
    }
}