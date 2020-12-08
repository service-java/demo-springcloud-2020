# README

- 芋道 Spring Boot 参数校验 Validation 入门
    - <http://www.iocoder.cn/Spring-Boot/Validation>
    - https://bval.apache.org/
    - https://github.com/spring-projects/spring-framework/blob/master/spring-aop/src/main/java/org/springframework/aop/framework/AopContext.java
    
```
世界比我们想象中的不安全

如果大量的错误数据成功入库??

比起枯燥的 CRUD 来说，它更枯燥

JSR303、JSR349、JSR380 三次标准的置顶

Bean Validation 和我们很久以前学习过的 JPA 一样，只提供规范，不提供具体的实现

Hibernate Validator
Hibernate ORM
Hibernate Search
Hibernate OGM 

@Validated内置支持 + 基于Spring AOP拦截

===
@AssertFalse
@AssertTrue

@Size(max, min)

@FutureOrPresent

@Email 
@Pattern(value) 

===
// Hibernate Validator 附加的约束注解
@Range(min=, max=)
@Length(min=, max=)
@URL(protocol=,host=,port=,regexp=,flags=)
@SafeHtml

===
// @Valid与@Validated  @diff
@Valid --> Bean Validation 所定义  --> 无法提供分组校验
@Validated --> Spring Validation 锁定义, 大部分场景下可使用


@Valid 注解的地方，多了【成员变量】 --> 嵌套校验

public class User {
    private String id;

    @Valid
    private UserProfile profile;
}

===
@EnableAspectJAutoProxy 
配置 exposeProxy = true

希望 Spring AOP 能将当前代理对象设置到 AopContext 中

===
对于 BindException 异常 --> 400 
对于 ConstraintViolationException 异常，没有特殊处理 --> 500

===
在类上，添加 @Validated 注解，表示 UserController 是所有接口都需要进行参数校验

但相比在 Controller 添加参数校验来说，在 Service 进行参数校验，会更加安全可靠

Controller 的参数校验可以不做
Service 的参数校验一定要做

===
// 处理校验异常
ServiceExceptionEnum + GlobalExceptionHandler

for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
    // 使用 ; 分隔多个错误
    if (detailMessage.length() > 0) {
        detailMessage.append(";");
    }
    // 拼接内容到其中
    detailMessage.append(constraintViolation.getMessage());
}

===
// 自定义约束 @todo

===
// 分组校验

===
// 手动校验
在少数业务场景下，我们可能需要手动使用 Bean Validation API

validator.validate()

===
// 国际化 i18n

===
Bean Validation 更多做的是，无状态的参数校验

@eg 不适合校验用户名是否唯一
```
