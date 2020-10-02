# README

- 芋道 Spring Boot Netty 入门
    - <http://www.iocoder.cn/Spring-Boot/Netty>]
    - http://www.iocoder.cn/Fight/Netty-to-solve-the-problem-of-sticky-and-unpacked-four-solutions

```
希望使用纯 Netty 实现简单的 IM 功能，支持身份认证、私聊消息、群聊消息
不会像直接使用 NIO 时，需要处理大量底层且细节的代码

心跳机制
断线重连

多Reactor多线程的模型

===
server = 配置+消息+消息处理器+Netty服务器

NettyServerHandlerInitializer
ChannelInitializer
空闲检测 --> 编码 --> 解码 --> 消息分发 --> 服务端处理

ChannelPipeline(ChannelHandler链) --> 由一系列的 ChannelHandler 组成

===
// client其实十分类似
client = 配置+消息+消息处理器+Netty客户端

NettyClientHandlerInitializer
ChannelInitializer
空闲检测 --> 编码 --> 解码 --> 消息分发 --> 服务端处理

// 差别在于
空闲检测额外增加 IdleStateHandler
客户端处理器换成了 NettyClientHandler

===
1) 连接
2) 数据读写

===
// 数据读写
二进制
Protobuf

把字符串转换成 byte 字节数组

// 粘包与拆包

粘包 --> 合并多个请求一同发送
可以关闭 Nagle 算法

拆包 --> 一次请求发送的数据量比较大，超过了缓冲区大小，TCP 就会将其拆分为多次发送

// 粘包和拆包的解决方案
1) 固定长度 @ignore
2) 包末尾的分隔符 @eg HTTP、WebSocket、Redis
3) 动态长度, 1)的升级版
将消息分为头部和消息体，在头部中保存有当前整个消息的长度，
只有在读取到足够长度的消息之后才算是读到了一个完整的消息

===
InvocationEncoder --> 将 Invocation 序列化，并写入到 TCP Socket 中
InvocationDecoder --> 实现从 TCP Socket 读取字节数组，反序列化成 Invocation

===
Message
认证+聊天+心跳

NettyServerConfig与NettyClientConfig配置
1) MessageHandlerContainer
2) MessageDispatcher

===
// 断开重连
定时重连

测试如下
启动 Netty Client，不要启动 Netty Server --> 不断发起定时重连

===
// 心跳机制与空闲检测
TCP 自带的空闲检测机制默认是 2 小时

业务实现逻辑
服务端发现 180 秒未从客户端读取到消息，主动断开连接
客户端发现 180 秒未从服务端读取到消息，主动断开连接

心跳机制
客户端每 60 秒向服务端发起一次心跳消息，保证服务端可以读取到消息
服务端在收到心跳消息时，回复客户端一条确认消息，保证客户端可以读取到消息

ReadTimeoutHandler

测试如下
启动 Netty Server 服务端，再启动 Netty Client 客户端，耐心等待 60 秒后，可以看到心跳日志

===
// 认证逻辑
AuthRequest
AuthRequestHandler
AuthResponse
AuthResponseHandler

测试如下
Postman 模拟一次认证请求
http://127.0.0.1:8080/test/mock

===
// 单聊逻辑
ChatSendToOneRequest
ChatSendToOneHandler

ChatSendResponse
ChatSendResponseHandler

ChatRedirectToUserRequest 转发消息给一个用户的请求
ChatRedirectToUserRequestHandler

===
// 群聊逻辑
ChatSendToAllRequest
```
