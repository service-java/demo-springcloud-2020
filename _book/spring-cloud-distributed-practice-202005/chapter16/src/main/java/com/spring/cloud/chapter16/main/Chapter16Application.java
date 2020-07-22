package com.spring.cloud.chapter16.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@RequestMapping("/redis")
public class Chapter16Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter16Application.class, args);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    // 注入StringRedisTemplate对象，该对象操作字符串，由Spring Boot机制自动装配
    @Autowired
    private StringRedisTemplate stringRedisTemplate = null;

    // 测试Redis写入
    @GetMapping("/write")
    public Map<String, String> testWrite() {
        Map<String, String> result = new HashMap<>();
        result.put("key1", "value1");
        stringRedisTemplate.opsForValue().multiSet(result);
        return result;
    }

    // 测试Redis读出
    @GetMapping("/read")
    public Map<String, String> testRead() {
        Map<String, String> result = new HashMap<>();
        result.put("key1", stringRedisTemplate.opsForValue().get("key1"));
        return result;
    }
}
