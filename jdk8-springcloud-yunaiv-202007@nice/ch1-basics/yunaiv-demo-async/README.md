# README

- 芋道 Spring Boot 异步任务入门
    - <http://www.iocoder.cn/Spring-Boot/Async-Job>
    - https://colobu.com/2016/02/29/Java-CompletableFuture
    - https://blog.csdn.net/javazejian/article/details/50896505 @nice

```
1) 需要高可靠性的时候 --> RabbitMQ、RocketMQ、Kafka 
2) 不需要 --> 使用进程内的队列或者线程池(JVM进程被异常关闭)+优雅关闭

Spring Task
@EnableAsync
@Async
@Transactional
基于Spring AOP拦截

===
Future

AsyncResult
ListenableFuture

CompletableFuture

AsyncResult 是作为异步执行的结果
addCallback必然直接使用回调处理执行的结果

===
// 异步异常处理器

AsyncUncaughtExceptionHandler 接口
但只能拦截返回类型非 Future 的异步调用方法

AsyncExecutionAspectSupport 是 AsyncExecutionInterceptor 的父类
返回类型为 Future 的异步调用方法, 需要异步回调处理

GlobalAsyncExceptionHandler

===
// 自定义执行器

===
// 注意
1) JVM 应用的正常优雅关闭，保证异步任务都被执行完成
2) 编写异步异常处理器 GlobalAsyncExceptionHandler ，记录异常日志，进行监控告警
```
