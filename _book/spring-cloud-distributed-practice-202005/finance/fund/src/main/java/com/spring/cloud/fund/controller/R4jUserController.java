package com.spring.cloud.fund.controller;

import com.spring.cloud.common.pojo.UserInfo;
import com.spring.cloud.fund.facade.UserFacade;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**** imports ****/
@RestController
@RequestMapping("/r4j")
public class R4jUserController {
    // 注册断路器注册机
    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry = null;

    // 注册限速器注册机
    @Autowired
    private RateLimiterRegistry rateLimiterRegistry = null;

    // OpenFeign接口
    @Autowired
    private UserFacade userFacade = null;

    @GetMapping("/user/{id}")
    public UserInfo getUser(@PathVariable("id") Long id) {
        // 获取断路器
        CircuitBreaker circuitBreaker
                = circuitBreakerRegistry.circuitBreaker("user");
        // 具体事件
        Callable<UserInfo> call = () -> userFacade.getUser(id);
        // 断路器绑定事件
        Callable<UserInfo> call1
                = CircuitBreaker.decorateCallable(circuitBreaker, call);
        // 获取限速器
        RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter("user");
        // 绑定限速器
        Callable<UserInfo> call2 =
                RateLimiter.decorateCallable(rateLimiter, call1);
        // 尝试获取结果
        Try<UserInfo> result = Try.of(() -> call2.call())
                // 降级逻辑
                .recover(ex->
                        new UserInfo(Long.MAX_VALUE, "", ex.getMessage()));
        return result.get();
    }
}