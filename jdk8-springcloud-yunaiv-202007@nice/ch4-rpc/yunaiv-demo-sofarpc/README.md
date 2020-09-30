# README

- 芋道 Spring Boot SOFARPC 入门
    - <http://www.iocoder.cn/Spring-Boot/SOFARPC>

```
SOFABolt 
是一套基于 Netty 实现的网络通信框架

SOFARPC 的整体架构和 Dubbo 是一致的

rpc-sofa-boot-starter

启动provider和consumer

访问地址
http://127.0.0.1:8080/user/get?id=1
http://127.0.0.1:8080/user/add?name=yudaoyuanma&gender=1
```

```
# 使用 Zookeeper 自带的客户端，连接到 Zookeeper 服务器
$ bin/zkCli.sh

# 查看 /sofa-rpc 目录下的所有服务。
# 此时，我们查看到了 UserRpcService 服务
$ ls /sofa-rpc
[cn.iocoder.springboot.lab62.rpc.api.UserRpcService]

# 查看 /sofa-rpc/cn.iocoder.springboot.lab62.rpc.api.UserRpcService 目录下的存储情况。
# 此时，我们看到了 providers 提供者信息
$ ls /sofa-rpc/cn.iocoder.springboot.lab62.rpc.api.UserRpcService
[configs, overrides, providers]

# 查看 UserRpcService 服务的节点列表
# 此时，可以看到有一个节点，就是我们刚启动的服务提供者。
$ ls /sofa-rpc/cn.iocoder.springboot.lab62.rpc.api.UserRpcService/providers
[bolt%3A%2F%2F192.168.43.240%3A12200%3Fversion%3D1.0%26accepts%3D100000%26appName%3Duser-service-provider%26weight%3D100%26language%3Djava%26pid%3D37225%26interface%3Dcn.iocoder.springboot.lab62.rpc.api.UserRpcService%26timeout%3D0%26serialization%3Dhessian2%26protocol%3Dbolt%26delay%3D-1%26dynamic%3Dtrue%26startTime%3D1591755744817%26id%3DuserRpcServiceImpl%26uniqueId%3D%26rpcVer%3D50700]
```

```
# 使用 Zookeeper 自带的客户端，连接到 Zookeeper 服务器
$ bin/zkCli.sh

# 查看 /sofa-rpc/cn.iocoder.springboot.lab62.rpc.api.UserRpcService 目录下的存储情况。
# 此时，我们看到了 consumers 提供者信息
$ ls /sofa-rpc/cn.iocoder.springboot.lab62.rpc.api.UserRpcService
[configs, consumers, overrides, providers]

# 查看 UserRpcService 服务的节点列表
# 此时，可以看到有一个节点，就是我们刚启动的服务消费者。
$ ls /sofa-rpc/cn.iocoder.springboot.lab62.rpc.api.UserRpcService/consumers
[bolt%3A%2F%2F10.101.16.12%3Fversion%3D1.0%26uniqueId%3D%26pid%3D38265%26timeout%3D-1%26id%3DuserRpcService%26generic%3Dfalse%26interface%3Dcn.iocoder.springboot.lab62.rpc.api.UserRpcService%26appName%3Duser-service-consumer%26serialization%3Dhessian2%26startTime%3D1591759947382%26pid%3D38265%26language%3Djava%26rpcVer%3D50605]
```
