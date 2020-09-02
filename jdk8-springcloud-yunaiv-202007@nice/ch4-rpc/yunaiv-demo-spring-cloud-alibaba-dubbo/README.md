# README

- 芋道 Spring Cloud Alibaba 服务调用 Dubbo 入门
    - http://www.iocoder.cn/Spring-Cloud-Alibaba/Dubbo
    - http://www.iocoder.cn/Fight/Java-microservices-framework-selection-Dubbo-and-Spring-Cloud
    - http://www.iocoder.cn/Spring-Boot/Validation
    - http://www.iocoder.cn/Fight/Dubbo-USES-the-JSR303-framework-hibernate-validator-to-encounter-ConstraintDescriptorImpl-could-not-be-instantiated

```
将 Dubbo 融合到 Spring Cloud 中，取代 Feign + Ribbon
提供更好的服务治理能力与更优的性能

spring-cloud-dependencies
spring-cloud-alibaba-dependencies
spring-cloud-starter-alibaba-nacos-discovery
spring-cloud-starter-dubbo

启动provider+consumer

访问地址
http://localhost:8848/nacos的服务列表
http://127.0.0.1:8080/user/get?id=1

===
resteasy-netty4
javax.servlet-api
spring-cloud-starter-alibaba-nacos-discovery --> 排除javax.ws.rs
spring-cloud-starter-openfeign

暴露的 Dubbo 服务的 rest:// 协议的端口 --> 9090

// 调用方式
1) Feign + Dubbo
2) Feign + Ribbon
3) RestTemplate + Dubbo
4) Dubbo

===
参数验证
JSR303 

javax.el --> 可能涉及到 EL 表达，所以引入，否则 hibernate-validator 在初始化会报错

测试地址 
http://127.0.0.1:8080/user/add POST

// @fix 反序列报错
因为 Hibernate ConstraintDescriptorImpl 没有默认空构造方法，
所以 Hessian 反序列化时，会抛出 HessianProtocolException 异常

1) 不要关闭掉服务消费者的参数校验功能 @nice
2) Service 接口上，不要抛出 ConstraintViolationException 异常 --> 被 Dubbo 内置的 ExceptionFilter 封装成 RuntimeException 异常，就不会存在反序列化的问题
3) 自定义实现拓展点 ->> 下面会讲到

===
自定义实现拓展点
ValidationFilter

AccessLogFilter、ExceptionFilter 

// Dubbo 改进了 JDK 标准的 SPI 的以下问题 @digest
1) JDK标准的 SPI 会一次性实例化扩展点所有实现 --> 初始化浪费且耗时
2) 如果扩展点加载失败，连扩展点的名称都拿不到了
3) 增加了对扩展点 IoC 和 AOP 的支持，一个扩展点可以直接 setter 注入其它扩展点

// 测试一下
POST 请求 http://127.0.0.1:8080/user/add 接口
传递参数为 name=yudaoyuanma 和 gender=1

===
整合 Sentinel
流量控制、熔断降级、系统负载保护

spring-cloud-starter-alibaba-sentinel
sentinel-apache-dubbo-adapter

访问
http://127.0.0.1:8080/user/get?id=1
http://127.0.0.1:7070/

新增流控规则

DubboFallback

Spring Boot Actuator
dubbo-spring-boot-actuator
默认关闭
访问 http://127.0.0.1:18080/actuator/bindings

```
