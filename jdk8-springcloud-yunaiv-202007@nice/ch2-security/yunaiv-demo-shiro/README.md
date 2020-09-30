# README

- 芋道 Spring Boot 安全框架 Shiro 入门
    - <http://www.iocoder.cn/Spring-Boot/Shiro>
    - https://waylau.com/apache-shiro-1.2.x-reference/index.html

```java
passport/护照 --> authentication/认证
ticket/机票 --> authorization/授权

// Realm
访问程序特定的安全数据如用户、角色、权限等的一个组件
特定安全的 DAO --> 身份验证（认证）和授权

getAuthenticationInfo()

// SecurityManager
SecurityManager 是 Shiro 架构的核心

// ShiroFilter

===
// shiro注解
@RequiresGuest
@RequiresAuthentication
@RequiresUser
@RequiresRoles(value = {"ADMIN", "NORMAL"}, logical = Logical.OR)
@RequiresPermissions(value = {"user:add", "user:update"}, logical = Logical.OR)

===
onAccessDenied
onLoginFailure

===
// 访问地址
http://localhost:9089/login
```
