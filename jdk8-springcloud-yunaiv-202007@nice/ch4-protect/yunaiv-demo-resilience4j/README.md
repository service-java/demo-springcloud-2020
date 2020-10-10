# README

- 芋道 Spring Boot 服务容错 Resilience4j 入门
    - <http://www.iocoder.cn/Spring-Boot/Resilience4j>
    - https://resilience4j.readme.io/docs/getting-started
    - https://github.com/apache/dubbo-samples/tree/master/java/dubbo-samples-resilience4j/dubbo-samples-resilience4j-filter
    - https://github.com/apache/dubbo-samples/tree/master/java/dubbo-samples-resilience4j/dubbo-samples-resilience4j-springboot2
    
```
轻量级的容错组件
灵感来自Hystrix

Resilience4j依赖Vavr库 --> Java8和函数式编程设计
但Hystrix依赖了 Archaius(多方依赖)

===
// 核心模块
resilience4j-circuitbreaker 熔断器
resilience4j-ratelimiter 限流
resilience4j-bulkhead 舱壁隔离
resilience4j-retry 自动重试
resilience4j-timelimiter 超时处理

resilience4j-cache 响应缓存??

// 其他模块
resilience4j-feign
resilience4j-spring-boot2(项目需要的依赖)

===
// CircuitBreaker 熔断器
3种状态
CLOSED、OPEN、HALF_OPEN

特殊状态
DISABLED（始终允许访问）和 FORCED_OPEN（始终拒绝访问）

设定失败率的阈值

Hystrix 是使用滑动窗口来进行存储的，
而 Resilience4j 采用的是 Ring Bit Buffer(环形缓冲区)
比boolean数组更节省内存

计算失败率需要填满环形缓冲区

===
// 测试一下
http://127.0.0.1:8080/demo/get_user?id=1 
关闭UserService
再次访问(getUserFallback) http://127.0.0.1:8080/demo/get_user?id=1 --> 返回mock:User:1
然后再疯狂这个链接 --> 直接 fallback 触发, 返回[getUserFallback][id(1) exception(CallNotPermittedException)]
接着开启UserService立刻疯狂访问 --> 会经历 打开 => 半开 => 关闭

===

Dubbo 已经提供了两种接入 Resilience4j的方法
dubbo-samples-resilience4j-filter 
dubbo-samples-resilience4j-springboot2 
```
