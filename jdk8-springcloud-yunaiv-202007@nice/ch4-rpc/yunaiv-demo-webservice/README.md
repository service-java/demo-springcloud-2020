# README

- 芋道 Spring Boot Web Services 入门
    - <http://www.iocoder.cn/Spring-Boot/Web-Services>

```
早期系统
跨平台、跨语言的远程调用
XML 是 Web Services 的基础
XML + HTTP

Web Services=SOAP(协议)+WSDL(描述语言)+UDDI(通用描述、发现及整合)
类似于 RESTful API + JSON + RESTful API 接口文档 + 注册中心

===
// SOAP
Simple Object Access Protocol
独立于平台
独立于语言

允许您绕过防火墙

// UDDI
Universal Description, Discovery and Integration
存储有关 Web Services 的信息的目录
由 WSDL 描述的网络服务接口目录
由 SOAP 进行通迅

// Spring Web Services
文档驱动
契约优先

spring-ws-core
spring-xml
spring-ws-security
spring-ws-support

XSD文件

所有 WebService 的请求必须以 Request 结尾，响应必须以 Response 结尾

// 依赖
spring-boot-starter-web-services
wsdl4j --> Java WSDL 实现库
jaxb2-maven-plugin --> 实现将 XML 生成目标类
```
