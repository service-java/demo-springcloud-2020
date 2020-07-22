package com.spring.cloud.ms.gateway.controller;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**** imports ****/
@RestController
public class CircuitBreakerController {

    // 断路器注册机 ①
    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry = null;
    @GetMapping("/test")
    public String test() {
        // 从断路器注册机中获取“test"断路器
        CircuitBreaker testCircuitBreaker
                = circuitBreakerRegistry.circuitBreaker("test");
        String url = "http://localhost:3001/test";
        RestTemplate restTemplate = new RestTemplate();
        // 描述事件并和断路器捆绑到一起 ②
        CheckedFunction0<String> decoratedSupplier =
                CircuitBreaker.decorateCheckedSupplier(
                        testCircuitBreaker,
                        ()-> restTemplate.getForObject(url, String.class));
        // 发送事件
        Try<String> result = Try.of(decoratedSupplier)
                // 如果发生异常，则执行降级方法
                .recover(ex -> "产生了异常"); // ③
        // 返回结果
        return result.get();
    }
}