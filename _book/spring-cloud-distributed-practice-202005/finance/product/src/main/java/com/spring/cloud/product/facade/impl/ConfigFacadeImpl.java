package com.spring.cloud.product.facade.impl;

import com.spring.cloud.common.pojo.UserInfo;
import com.spring.cloud.product.facade.ConfigFacade;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**** imports ****/
@Service
public class ConfigFacadeImpl implements ConfigFacade {
    @Autowired
    private RestTemplate restTemplate = null;

    // 断路器注册机
    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry = null; // ①

    @Override
    public UserInfo getUserWithCircuitBreaker(Long id) {
        // 从断路器注册机中获取“user”断路器
        CircuitBreaker userCircuitBreaker
                = circuitBreakerRegistry.circuitBreaker("user"); // ②
        // 描述事件
        CheckedFunction0<UserInfo> decoratedSupplier =
                CircuitBreaker.decorateCheckedSupplier(
                        userCircuitBreaker, () -> reqUser(id));
        // 发送事件
        Try<UserInfo> result = Try.of(decoratedSupplier)
                // 如果发生异常，则执行降级方法
                .recover(ex -> {
                    return new UserInfo(Long.MIN_VALUE, null, null);
                });
        // 返回结果
        return result.get();
    }


    // Ribbon服务调用
    private UserInfo reqUser(Long id) {
        String url = "http://USER/user/info/{id}";
        System.out.println("获取用户" + id);
        UserInfo user = restTemplate.getForObject(url, UserInfo.class, id);
        return user;
    }

    // 限速器注册机
    @Autowired
    private RateLimiterRegistry rateLimiterRegistry = null; // ①

    @Override
    public UserInfo getUserWithRatelimiter(Long id) {
        // 从限速器注册机中获取"user"限速器
        RateLimiter userRateLimiter
                = rateLimiterRegistry.rateLimiter("user"); // ②
        // 描述事件
        CheckedFunction0<UserInfo> decoratedSupplier =
                RateLimiter.decorateCheckedSupplier(
                        userRateLimiter, ()->reqUser(id));
        // 发送事件
        Try<UserInfo> result = Try.of(decoratedSupplier)
                // 如果发生异常，则执行降级方法
                .recover(ex -> {
                    return new UserInfo(Long.MIN_VALUE, null, null);
                });
        // 返回结果
        return result.get();
    }
}
