# README

- 芋道 Spring Cloud 注册中心 ZooKeeper 入门
    - <http://www.iocoder.cn/Spring-Cloud/ZooKeeper-Discovery>

```java
zk的注册中心，当服务的实例列表发生变化（新增或者移除）时，通知订阅该服务的 Consumer，从而让 Consumer 能够刷新本地缓存

Eureka 注册中心，并不提供通知功能，而是 Eureka Client 自己定期轮询，实现本地缓存的更新


spring-cloud-starter-zookeeper-discovery --> 将 ZooKeeper 作为注册中心，并实现对其的自动配置

===

bin/zkCli.sh
ls /services

ls /services/demo-provider
ls /services/demo-provider/945e2296-5769-4cb9-a20d-8fade1f418b4(某ID)

// 访问
http://localhost:18080/echo?name=demo
http://localhost:28080/hello?name=demo
关闭provider
http://localhost:28080/hello?name=demo
```
