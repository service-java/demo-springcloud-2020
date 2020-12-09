# README

- 芋道 Spring Cloud 服务容错 Resilience4j 入门
    - <http://www.iocoder.cn/Spring-Cloud/Resilience4j>

```
轻量级的容错组件
灵感来自于 Hystrix
但主要为 Java 8 和函数式编程所设计

接入 Spring Cloud Context 提供的 RefreshScope 机制

// 依赖
resilience4j-spring-boot2
resilience4j-spring-cloud2

===
spring-cloud-starter-circuitbreaker-resilience4j

Spring Cloud Circuit Breaker
Spring Cloud 定义的熔断器统一抽象模型

===
暂时比较推荐使用 resilience4j-spring-cloud2 的方式，功能更强大
```

# 本地运行

- http://127.0.0.1:8080/demo/get_user?id=1
- http://127.0.0.1:8080/rate-limiter-demo/get_user?id=1
- 快速请求6次

```
mock:User:1
```
