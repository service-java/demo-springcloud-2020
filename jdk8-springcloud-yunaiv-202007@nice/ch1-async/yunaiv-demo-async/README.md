# README

- 芋道 Spring Boot 异步任务入门
    - <http://www.iocoder.cn/Spring-Boot/Async-Job>

```
1) 需要高可靠性的时候 --> RabbitMQ、RocketMQ、Kafka 
2) 不需要 --> 使用进程内的队列或者线程池(JVM进程被异常关闭)+优雅关闭

Spring Task
@Async
@Transactional
基于Spring AOP拦截


```
