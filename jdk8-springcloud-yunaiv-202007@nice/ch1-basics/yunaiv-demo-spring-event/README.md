# README

- 芋道 Spring Boot 事件机制 Event 入门
    - <http://www.iocoder.cn/Spring-Boot/Event>

```
观察者模式
用来实时事件处理系统

@eg 发送邮件、发送优惠劵

===
观察者模式和发布订阅模式 @diff
Event Channel

===
Spring 事件机制
ApplicationEvent
ApplicationEventPublisher
ApplicationListener

ContextStartedEvent
ContextStoppedEvent
ContextClosedEvent
ContextRefreshedEvent

ApplicationStartingEvent --> 启动开始
ApplicationEnvironmentPreparedEvent --> 环境准备完成
ApplicationContextInitializedEvent
ApplicationPreparedEvent
ApplicationReadyEvent
ApplicationFailedEvent


// 两种listener方式
1) @EventListener
2) implements ApplicationListener<UserRegisterEvent>

// 测试一番
http://127.0.0.1:8080/demo/register?username=yudaoyuanma

===
RouteRefreshListener --> 监听以实现网关路由动态刷新
RefreshRemoteApplicationEvent --> 结合 RabbitMQ 作为 Spring Cloud Bus 消息总线 + 本地配置刷新

===
实现 Ordered 接口，指定其顺序

@TransactionalEventListener
```
