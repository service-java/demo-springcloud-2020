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
# 下载slow
curl -sSL https://zipkin.io/quickstart.sh | bash -s
java -jar zipkin.jar

# 运行zipkin服务端, 端口9411
RABBIT_ADDRESSES=localhost java -jar zipkin.jar
```

- 再启动其他服务

```
gateway
uaa

admin
monitor

user
blog
```

- 访问
    - http://localhost:8761/ --> 除了config-server与自身
    - http://localhost:8762/swagger-ui.html#/
    - http://localhost:8763/swagger-ui.html#/
    - http://localhost:9411/zipkin/ --> blog-service服务依赖了user-service服务
    - http://localhost:9998/ --> 账号密码: admin/admin

- 测试

```bash
# 1) 创建一个用户
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d ' { "password": "123456", "username": "miya" }' 'http://localhost:5000/userapi/user/registry'

# 2) 尝试登录 --> ok
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' 'http://localhost:5000/userapi/user/login?username=miya&password=123456'

# 3）获取用户信息 --> 因为没有角色权限, 不允许访问
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'Authorization: Bearer ${Token}' 'http://localhost:5000/userapi/user/miya'
# @demo
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTUzOTk1OTIsInVzZXJfbmFtZSI6Im1peWEiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiMTZmMTA5ZDgtOTk1Ni00MzI4LThmMTgtNDBjNGNmNDJkNzQ1IiwiY2xpZW50X2lkIjoidWFhLXNlcnZpY2UiLCJzY29wZSI6WyJzZXJ2aWNlIl19.QVofJ3CRwOjJTxQFLwpF0kyRyaagiOZC_DzVnI93GSxUYwrarZwMSaj82fe1M-ToR7mpPbWRAi-8zFBxwuIIIDY1cX19QODhhvdAo11ddIXSrOtWGitg3t-fwJOyGjL78yFlg2IKeMSyYlOvu4wi2xMTAxY8l73UDklZc6To6SK38H1kCJCFCsK6Lo6avlzoWdzFUmWHK6CKIaMBVC21mMyqbqRVBG2tW03gVSEXzVXJdTa1WE6jBxoGf2jBRuy1_qN4lNkYqfyuZeyNpheUFgpWoTBg2e7R5i-hJc3aSOif2R0zJZEQSt7o-ePgRKHXSr4k_AS37OhLAtzCvxWupA' 'http://localhost:5000/userapi/user/miya'

# 4) 数据库插入一条数据后, 重新获取Token, 再次尝试登录 --> ok
INSERT INTO user_role VALUES('13', '1')
```

# 常见问题 @faq

- 关于uaa

```
1) 启动gateway5000和user服务8762, 但不启动uaa9999
会获取不到token

但已获取的token后, 关掉uaa也可以正常访问userInfo 

2) 启动uaa9999和user服务8762, 但不启动gateway5000
连不上5000, 拒绝连接
```
