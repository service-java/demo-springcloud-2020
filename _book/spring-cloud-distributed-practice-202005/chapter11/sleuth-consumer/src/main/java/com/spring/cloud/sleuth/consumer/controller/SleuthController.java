package com.spring.cloud.sleuth.consumer.controller;

import com.spring.cloud.sleuth.consumer.feign.SleuthFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**** imports ****/
@RestController
public class SleuthController {
    // 日志
    private static final Logger logger
            = LoggerFactory.getLogger(SleuthController.class);

    @Autowired
    private RestTemplate restTemplate = null;

    @Autowired
    private SleuthFeign sleuthFeign = null;

    // Ribbon的调用
    @GetMapping("/hello/rest/{name}")
    public String testResTemplate(@PathVariable("name") String name) {
        logger.info("使用RestTemplate，请求参数：{}", name );
        String url = "http://sleuth-provider/hello/{name}";
        logger.info("使用RestTemplate，请求URL：{}", url );
        String result = restTemplate.getForObject(url, String.class, name);
        logger.info("使用RestTemplate，请求结果：{}", result );
        return result;
    }

    // OpenFeign的调用
    @GetMapping("/hello/feign/{name}")
    public String testFeign(@PathVariable("name") String name) {
        logger.info("使用Open Feign，请求参数：{}", name );
        String result = sleuthFeign.sayHello(name);
        logger.info("使用Open Feign，请求结果：{}", result );
        return result;
    }
}