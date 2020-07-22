package com.spring.cloud.sleuth.provider.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**** imports ****/
@RestController
public class SleuthProviderController {
    // 日志对象（org.slf4j.Logger）
    private static final Logger logger
            = LoggerFactory.getLogger(SleuthProviderController.class);

    // 提供的服务
    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable("name") String name)  {
        logger.info("请求参数：{}" , name);
        String helloResult = "hello " + name;
        logger.info("请求结果：{}", helloResult);
        return helloResult;
    }
}