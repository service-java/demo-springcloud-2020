package com.spring.cloud.msclient1.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**** imports ****/

// 声明Feign客户端，并指定调用的服务
@FeignClient(value="client2")
public interface FeignTestFacade {

    // REST风格服务调用
    @GetMapping("/feign/test")
    public String feignTest();
}