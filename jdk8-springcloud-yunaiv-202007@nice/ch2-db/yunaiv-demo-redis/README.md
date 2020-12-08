# README

- 芋道 Spring Boot Redis 入门
    - <http://www.iocoder.cn/Spring-Boot/Redis>
    - https://github.com/spring-projects/spring-session/issues/789
    - https://github.com/spring-projects/spring-data-redis/blob/64b89137648f6c0e0c810c624e481bcfc0732f4e/src/main/java/org/springframework/data/redis/core/RedisConnectionUtils.java#L159
    
```
Spring Data Redis 提供了统一的操作模板

从 Jedis 迁移成 Lettuce 来

Redisson 是由 redisson-spring-data 来提供

个人推荐的话，生产中还是使用 Jedis ，稳定第一

SkyWalking 中间件，暂时只支持 Jedis 的自动化的追踪，那么更加考虑使用 Jedis 啦

===

序列化

RedisSerializer

1) JDK 序列化方式 @ignore --> 一串奇怪的 16 进制字符, 线上查看不便 
2) String 序列化方式
3) JSON 序列化方式
Jackson2JsonRedisSerializer  @ignore

4) XML 序列化方式 @ignore
Spring OXM

===
配置序列化方式

===
Cache Object + DO + dao + service

JSONUtil.parseObject(value, UserCacheObject.class)
operations.set(buildKey(id), JSONUtil.toJSONString(object));

===
// Pipeline、Transaction、Pub/Sub、Script

Pipeline
批量操作，提升性能必备神器
Pipeline + RedisCallback 

Transaction @ignore
Redis Transaction 实际创建事务的前提，是当前已经存在 Spring Transaction 
建议把事务的、和非事务的 RedisTemplate 拆成两个连接池，相互独立

Pub/Sub @ignore --> 使用 Kafka、或者 RocketMQ 的广播消费功能，更加可靠有保障
简单的订阅功能, 不是一个可靠的订阅系统 --> Redis 5.0 版本后，正式发布 Stream 功能
RedisMessageListenerContainer 是基于一次 SUBSCRIBE 或 PSUBSCRIBE 命令
所以不支持不同类型的 Topic

@demo Dubbo 使用 Redis 作为注册中心时，使用 Redis Pub/Sub 实现注册信息的同步

Script

===
Redisson
Java 最强的 Redis 客户端 @eg  Redis 分布式锁、BloomFilter 布隆过滤器

===
Redis 分布式锁

考虑性能为优先因素，不需要特别绝对可靠性的场景下，我们会优先考虑使用 Redis 实现的分布式锁
Redisson给了8 种锁

===
Redis 限流器
但不是严格且完整的滑动窗口的限流器实现
```

- Redisson 的每个配置项的解释
    - https://blog.csdn.net/zl_momomo/article/details/82788294

```yml
clusterServersConfig:
  # 连接空闲超时 如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，那么这些连接将会自动被关闭，并从连接池里去掉。时间单位是毫秒。
  idleConnectionTimeout: 10000
  pingTimeout: 1000
  # 连接超时
  connectTimeout: 10000
  # 命令等待超时
  timeout: 3000
  # 命令失败重试次数
  retryAttempts: 3
  # 命令重试发送时间间隔
  retryInterval: 1500
  # 重新连接时间间隔
  reconnectionTimeout: 3000
  # failedAttempts
  failedAttempts: 3
  # 密码
  password: null
  # 单个连接最大订阅数量
  subscriptionsPerConnection: 5
  # 客户端名称
  clientName: null
  #负载均衡算法类的选择  默认轮询调度算法RoundRobinLoadBalancer
  loadBalancer: !<org.redisson.connection.balancer.RoundRobinLoadBalancer> {}
  slaveSubscriptionConnectionMinimumIdleSize: 1
  slaveSubscriptionConnectionPoolSize: 50
  # 从节点最小空闲连接数
  slaveConnectionMinimumIdleSize: 32
  # 从节点连接池大小
  slaveConnectionPoolSize: 64
  # 主节点最小空闲连接数
  masterConnectionMinimumIdleSize: 32
  # 主节点连接池大小
  masterConnectionPoolSize: 64
  # 只在从服务节点里读取
  readMode: "SLAVE"
  # 主节点信息
  nodeAddresses:
  - "redis://192.168.56.128:7000"
  - "redis://192.168.56.128:7001"
  - "redis://192.168.56.128:7002"
  #集群扫描间隔时间 单位毫秒
  scanInterval: 1000
threads: 0
nettyThreads: 0
codec: !<org.redisson.codec.JsonJacksonCodec> {}
```
