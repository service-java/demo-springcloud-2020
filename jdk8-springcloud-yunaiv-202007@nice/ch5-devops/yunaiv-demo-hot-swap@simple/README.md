# README

- 芋道 Spring Boot 热部署入门 @simple
    - <http://www.iocoder.cn/Spring-Boot/hot-swap>

```
HotSwap 

热部署、热更新、热替换、热加载

===
spring-boot-devtools --> 不是热部署，而是一种较快的重启方式
1) 不改变的类（例如，第三方 jar）被加载到 base 类加载器中。
2) 经常处于开发状态的类被加载到 restart 类加载器中
重启时仅restart 类加载器被丢弃, 会比冷启动快

compiler.automake.allow.when.app.running

===
IDEA 自带了热部署

Build project automatically

需要焦点从 IDEA 离开

===
JRebel --> 通过在加载类时重写类来加快重新加载 --> 必要性不大
```
