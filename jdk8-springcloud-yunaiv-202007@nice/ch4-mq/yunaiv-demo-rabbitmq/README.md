# README

- 芋道 Spring Boot 消息队列 RabbitMQ 入门
    - <http://www.iocoder.cn/Spring-Boot/RabbitMQ/?github>
    - http://www.iocoder.cn/RabbitMQ/install/
    - http://www.iocoder.cn/RabbitMQ/yuliu/doc/
    - https://mvnrepository.com/artifact/org.springframework.amqp/spring-amqp
    - https://mvnrepository.com/artifact/org.springframework.amqp/spring-rabbit

```
Spring Cloud 在消息队列主推 RabbitMQ ??

// 功能
Producer 发送顺序消息，Consumer 顺序消费消息
Producer 发送定时消息
Producer 批量发送消息
Producer 发送事务消息
Consumer 广播和集群消费消息
Consumer 批量消费消息

Spring-AMQP -->  AMQP 的基础抽象
spring-rabbit --> 基于 RabbitMQ 对 AMQP 的具体实现

// 功能特性
Listener --> 监听器容器, 异步处理接收到的消息
RabbitTemplate --> 发送和接收消息
RabbitAdmin --> 自动创建队列，交换器，绑定器

Direct、Topic、Fanout、Headers 

spring-boot-starter-amqp
```
