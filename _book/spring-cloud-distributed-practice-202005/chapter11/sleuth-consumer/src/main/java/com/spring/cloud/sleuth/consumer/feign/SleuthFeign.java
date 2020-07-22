package com.spring.cloud.sleuth.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 声明为OpenFeign客户端
@FeignClient("sleuth-provider")
public interface SleuthFeign {

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable("name") String name);
}
