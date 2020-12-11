# README

- 芋道 Spring Boot WebSocket 入门
    - http://www.iocoder.cn/Spring-Boot/WebSocket
    - https://tech.meituan.com/2017/03/17/shark-sdk.html @nice
    - http://www.iocoder.cn/Fight/Clarify-the-relationship-between-WebSocket-and-HTTP @nice
    - https://www.ruanyifeng.com/blog/2017/05/websocket.html @nice
    - https://blog.csdn.net/John_62/article/details/78208177
    - https://juejin.cn/post/6844903588297179149
    - https://www.xncoding.com/2017/07/15/spring/sb-websocket.html
    - https://blog.csdn.net/achenyuan/article/details/80851512
    - http://www.52im.net/thread-294-1-1.html @nice

```
// WebSocket协议
重点是提供服务端主动向客户端发送数据的能力
@eg 聊天 IM 即使通讯功能、消息订阅服务、网页游戏

使用 TCP 通信

===
// 3种实现方案
1) Spring WebSocket
内置了对 STOMP 协议的支持

2) Tomcat WebSocket
https://www.cnblogs.com/xdp-gacl/p/5193279.html

3) Netty WebSocket ->> 
https://netty.io/news/2012/11/15/websocket-enhancement.html
@eg 除了支持 WebSocket 协议，还能提供原生的 Socket 协议

===
// JSR-356 规范
https://www.oracle.com/technical-resources/articles/java/jsr356.html

Javax WebSocket

===
提供如下功能
1) 身份认证请求
2) 私聊消息
3) 群聊消息

spring-boot-starter-websocket

WebsocketServerEndpoint

@OnOpen
@OnMessage
@OnClose
@OnError

ServerEndpointExporter --> 扫描添加有 @ServerEndpoint 注解的 Bean

===
基于 Message 消息的异步模型

{
    type: "", // 消息类型
    body: {} // 消息体
}

===
用户不处于在线的时候，消息需要持久化到数据库

客户端网络环境较差 @eg 移动端场景下，出现网络闪断
--> 客户端的 ACK 消息机制

// 2种解决方案
1) 无论客户端是否在线，服务端都先把接收到的消息持久化到数据库中 @ignore
需要定时轮询
2) 推拉结合：基于滑动窗口 ACK
拉取增量消息列表

在分布式消息队列、配置中心、注册中心实现实时的数据同步，经常被采用

客户端和服务端不一定需要使用长连接，也可以使用长轮询替代
```

- 使用easyswoole进行测试
    - http://www.easyswoole.com/wstool.html
