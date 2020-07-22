package com.spring.cloud.chapter19.utils;


import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

public class R4jUtils {

    // 断路器注册机
    private static CircuitBreakerRegistry registry = null;

    // 返回断路器注册机
    public static CircuitBreakerRegistry CircuitBreakerRegistry() {
        // 为null时创建实例
        if (registry == null) {
            // 采用默认配置
            CircuitBreakerConfig config
                    = CircuitBreakerConfig.ofDefaults();
            // 创建注册机
            registry = CircuitBreakerRegistry.of(config);
        }
        // 返回断路器注册机
        return registry;
    }
}
