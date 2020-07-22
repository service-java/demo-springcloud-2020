package com.spring.cloud.ms.gateway.rest;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import org.springframework.web.client.RestTemplate;

public class BulkheadMain {

    // 舱壁隔离配置
    private static BulkheadConfig bulkheadConfig = null;

    // 初始化舱壁配置
    private static BulkheadConfig initBulkheadConfig() {
        if (bulkheadConfig == null) {
            // 舱壁配置
            bulkheadConfig = BulkheadConfig.custom()
                    // 最大并发数，默认为25
                    .maxConcurrentCalls(20)
                    /* 调度线程最大等待时间（单位毫秒），默认值0，
                     如果存在高并发的场景，强烈建议设置为0,
                     如果设置为非0，那么在高并发的场景下，
                    可能导致线程积压的后果*/
                    .maxWaitTime(0)
                    .build();
        }
        return bulkheadConfig;
    }

    // 舱壁注册机
    private static BulkheadRegistry bulkheadRegistry;

    // 初始化舱壁注册机
    private static BulkheadRegistry initBulkheadRegistry() {
        if (bulkheadConfig == null) { // 初始化
            initBulkheadConfig();
        }
        if (bulkheadRegistry == null) {
            // 创建舱壁注册器，并设置默认配置
            bulkheadRegistry = BulkheadRegistry.of(bulkheadConfig);
            // 创建一个命名为test的舱壁
            bulkheadRegistry.bulkhead("test");
        }
        return bulkheadRegistry;
    }

    public static void main(String[] args) {
        initBulkheadRegistry(); // 初始化
        RestTemplate restTemplate = new RestTemplate();
        // 获取舱壁
        Bulkhead bulkhead = bulkheadRegistry.bulkhead("test");
        String url = "http://localhost:3001/test";
        // 描述事件 ①
        CheckedFunction0<String> decoratedSupplier
                = Bulkhead.decorateCheckedSupplier(
                bulkhead, () ->
                        restTemplate.getForObject(url, String.class));
        // 尝试
        Try<String> result = Try.of(decoratedSupplier)
                .recover(ex -> { // 降级服务
                    ex.printStackTrace();
                    return "服务调用失败";
                });
        // 发送请求
        System.out.println(result.get());
    }
}
