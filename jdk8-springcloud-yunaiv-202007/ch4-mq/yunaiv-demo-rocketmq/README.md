# README

- 芋道 Spring Boot 消息队列 RocketMQ 入门
    - <http://www.iocoder.cn/Spring-Boot/RocketMQ/?github>
    - http://www.iocoder.cn/RocketMQ/install/?self
    - https://spring.io/projects/spring-amqp
    - https://docs.spring.io/spring-boot/docs/1.2.2.RELEASE/reference/html/boot-features-messaging.html

```
低延时的、高可靠的消息发布与订阅服务

2018-12 RocketMQ 团队发布 RocketMQ 集成到 Spring Boot 的解决方案 --> 偏晚

// 特点
1) 能够保证严格的消息顺序
2) 提供丰富的消息拉取模式
3) 高效的订阅者水平扩展能力
4) 实时的消息订阅机制
5) 亿级消息堆积能力

// 角色
生产者 Producer --> @eg 发送事务消息
消费者 Consumer --> @eg 广播和集群消费消息

RocketMQ-Spring

Spring Message 

Spring AMQP

@RocketMQMessageListener 
```

```java
@Autowired
private RocketMQTemplate rocketMQTemplate;

public void asyncSend(Integer id, SendCallback callback) {
    // 创建 Demo01Message 消息
    Demo01Message message = new Demo01Message();
    message.setId(id);
    // 异步发送消息
    rocketMQTemplate.asyncSend(Demo01Message.TOPIC, message, callback);
}
```
