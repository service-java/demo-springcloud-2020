# README

- 芋道 Spring Boot 分库分表入门
    - <http://www.iocoder.cn/Spring-Boot/sharding-datasource/?github>

```
尽可能查询的时候，带有片键（分库分表字段）

明明只有一条 Logic SQL 操作，却发起了 8 条 Actual SQL 操作

使用 HintManager 强制访问主库
```
