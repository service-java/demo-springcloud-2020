# README

- 芋道 Spring Boot 服务容错 Sentinel 入门
    - <http://www.iocoder.cn/Spring-Boot/Sentinel/?github>

```
调配器
把随机的请求调整成合适的形状

// 流量控制
1) 资源的调用关系 --> 资源的调用链路，资源和资源之间的关系
2) 运行指标 --> QPS、线程池、系统负载
3) 控制的效果 --> 直接限流、冷启动、排队

sentinel-core
sentinel-transport-simple-http
sentinel-spring-webmvc-adapter

addSentinelWebInterceptor(registry)



```
