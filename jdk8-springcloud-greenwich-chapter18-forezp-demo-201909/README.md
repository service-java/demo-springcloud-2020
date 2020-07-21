# README

- 深入理解Spring Cloud与微服务构建（第2版）- 综合demo

# 本地构建

- 准备JDK 1.8、Maven 3.x、RabbitMQ、MySQL环境
- 初始化_sql目录下的数据库
- 修改user-service、uaa-service、blog-service和log-service的配置文件 

```bash
MySQL数据库
RabbitMQ的连接配置信息
```

- 依次启动eureka-server、config-server和zipkin

```bash
# slow
curl -sSL https://zipkin.io/quickstart.sh | bash -s
java -jar zipkin.jar

# 运行zipkin服务端
RABBIT_ADDRESSES=localhost java -jar zipkin.jar
```

- 再启动
