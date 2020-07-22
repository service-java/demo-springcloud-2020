package com.spring.cloud.gateway.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.cloud.common.vo.ResultMessage;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.vavr.control.Try;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Callable;

@SpringBootApplication(scanBasePackages = "com.spring.cloud.gateway" )
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

//    /**
//     * 创建路由规则
//     * @param builder -- 路由构造器
//     * @return 路由规则
//     */
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes() //开启路由配置
//                // 匹配路径
//                .route(f -> f.path("/user/**") // route方法逻辑是一个断言，后续会论述
//                        // 转发到具体的URI
//                        .uri("http://localhost:6001"))
//                // 创建
//                .build();
//    }

//    /**
//     * 创建路由规则
//     * @param builder -- 路由构造器
//     * @return 路由规则
//     */
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        ZonedDateTime datetime = LocalDateTime.now()//获取当前时间
//                // 两分钟后路由失效
//                .plusMinutes(2)
//                // 定义国际化区域
//                .atZone(ZoneId.systemDefault()); // 定义UTC时间 ①
//        return builder.routes()
//                // 匹配
//                .route("/user/**", r -> r.before(datetime) // 使用断言 ②
//                        // 转发到具体的URI
//                        .uri("http://localhost:6001"))
//                // 创建
//                .build();
//    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        ZonedDateTime datetime = LocalDateTime.now()//获取当前时间
//                // 当前时间往后1分钟
//                .plusMinutes(1) // ①
//                // 设置国际化区域
//                .atZone(ZoneId.systemDefault());
//        return builder.routes()
//                // 设置路由和After断言
//                .route("/user/**", r -> r.after(datetime) // ②
//                        // 匹配路径
//                        .uri("http://localhost:6001"))
//                .build();
//    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        // 构建时间节点1
//        ZonedDateTime datetime1 = LocalDateTime.now()//获取当前时间
//                // 当前时间往后1分钟
//                .plusMinutes(1)
//                // 设置国际化区域
//                .atZone(ZoneId.systemDefault());
//        // 构建时间节点2
//        ZonedDateTime datetime2 = LocalDateTime.now()//获取当前时间
//                // 当前时间往后2分钟
//                .plusMinutes(2)
//                // 设置国际化区域
//                .atZone(ZoneId.systemDefault());
//        return builder.routes()
//                // 设置路由和Between断言
//                .route("/user/**", r -> r.between(datetime1, datetime2) // ①
//                        // 匹配路径
//                        .uri("http://localhost:6001"))
//                .build();
//    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                // 设置路由和Cookies断言，
//                // 判定Cookies中名称为“cookies_id”的参数是否匹配正则式“abcd.*”
//                .route("/user/**", r -> r.cookie("cookies_id", "abcd.*") // ①
//                        // 匹配路径
//                        .uri("http://localhost:6001"))
//                .build();
//    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        // 两个正则式
//        String host1 = "**.host.com:3001";
//        String host2 = "**.myhost.com:3001";
//        return builder.routes()
//                // 设置路由和Host断言，
//                .route("/user/**", r -> r.host(host1, host2) // ①
//                        // 匹配路径
//                        .uri("http://localhost:6001"))
//                .build();
//    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                // 设置路由和Method断言，
//                .route("/user/**", r -> r.method(HttpMethod.GET) // ①
//                        // 匹配路径
//                        .uri("http://localhost:6001"))
//                .build();
//    }

//    // Method断言
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        String path1 = "/user/info/{id}";
//        String path2 = "/user/infoes2";
//        return builder.routes()
//                // 设置路由和Path断言，
//                .route("/user/**", r -> r.path(path1, path2) // ①
//                        // 匹配路径
//                        .uri("http://localhost:6001"))
//                .build();
//    }

//    // Query断言
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        String regex = "^[0-9]*$"; // 0～9的数字 ①
//        return builder.routes()
//                // 设置路由和Method断言，要求参数和正则式匹配
//                .route("/user/**", r-> r.query("id", regex) // ②
//                        // .route("/user/**", r -> r.query("id") // 要求存在参数  ③
//                        // 匹配路径
//                        .uri("http://localhost:6001"))
//                .build();
//    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        // 192.168.234.0为IP地址，0为子掩码
//        String addr1 = "192.168.80.0/0";
//        // 192.168.235.0为IP地址，32为子掩码
//        String addr2 = "192.168.80.99/32";
//        return builder.routes()
//                // 设置路由和RemoteAddr断言，要求远程服务器地址属于某个网段
//                .route("/user/**", r-> r.remoteAddr(addr1, addr2) // ①
//                        // 匹配路径
//                        .uri("http://localhost:6001"))
//                .build();
//    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        // 组名
//        String groupName = "user_info"; // ①
//        // 请求路径
//        String path ="/user/info/{id}";
//        return builder.routes()
//                // 第一个路由
//                .route("user_info_v1", r -> r.path(path)// 定义路径
//                        .and() // and连接词，表示并且
//                        .weight(groupName, 80) // 设置组名和权重 ②
//                        // 匹配路径，路由到6001端口的用户微服务实例
//                        .uri("http://localhost:6001"))
//                // 第二个路由
//                .route("user_info_v2", r -> r.path(path) // 定义路径
//                        .and() // and连接词，表示并且
//                        .weight(groupName, 20) // 设置组名和权重 ③
//                        // 匹配路径，路由到6002端口的用户微服务实例
//                        .uri("http://localhost:6002"))
//                .build();
//    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                // 设置请求路径满足ANT风格“/user/**”的路由
//                .route("user-service", r-> r.path("/user/**")
//                        // 通过增加请求头过滤器添加请求头参数
//                        .filters(f->f.addRequestHeader("id", "1")) // ①
//                        // 匹配路径
//                        .uri("http://localhost:6001"))
//                .build();
//    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                // 设置请求路径满足ANT风格“/user/**”的路由
//                .route("user-service", r-> r.path("/user/**")
//                        // 通过增加请求参数过滤器添加请求参数
//                        .filters(f->f.addRequestParameter("id", "1")) // ①
//                        // 匹配路径
//                        .uri("http://localhost:6001"))
//                .build();
//    }


//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                // 设置请求路径满足ANT风格“/user/**”的路由
//                .route("user-service", r-> r.path("/user/**")
//                        // 通过增加响应头过滤器添加响应参数
//                        .filters(f->f.addResponseHeader("token", "a123456789")) // ①
//                        // 匹配路径
//                        .uri("http://localhost:6001"))
//                .build();
//    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                // 设置请求路径满足ANT风格“/user/**”的路由
//                .route("hystrix-service", r-> r.path("/hystrix/**")
//                        // 重试2次（最多尝试路由3次，其中2次是重试）
//                        .filters(f->f.retry(2)) // ①
//                        // 匹配路径
//                        .uri("http://localhost:6001"))
//                .build();
//    }

//    // Retry过滤器工厂
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                // 设置请求路径满足ANT风格“/user/**”的路由
//                .route("hystrix-service", r-> r.path("/hystrix/**")
//                        // 使用重试配置，配置重试
//                        .filters(f->f.retry(retryConfig ->{ // ①
//                            // 重试次数为2
//                            retryConfig.setRetries(2);
//                            // 接受HTTP的请求方法，限制范围为GET、POST和PUT
//                            retryConfig.setMethods(HttpMethod.GET,
//                                    HttpMethod.POST, HttpMethod.PUT);
//                            // 限制重试的响应码为服务器错误（500）和坏请求（400）
//                            retryConfig.setStatuses(HttpStatus.INTERNAL_SERVER_ERROR,
//                                    HttpStatus.BAD_REQUEST);
//                            //  限制重试的系列为服务器错误
//                            retryConfig.setSeries(HttpStatus.Series.SERVER_ERROR);
//                        }))
//                        // 匹配路径
//                        .uri("http://localhost:6001"))
//                .build();
//    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                // 设置请求路径满足ANT风格“/hystrix/**”的路由
//                .route("hystrix-service", r-> r.path("/hystrix/**")
//                        // 通过增加Hystrix过滤器，提供熔断功能
//                        .filters(f->f.hystrix(config->{ // ①
//                            // Hystrix命令名称
//                            config.setName("hystrix-cmd");
//                            // 降级跳转URI
//                            config.setFallbackUri("forward:/gateway/fallback");
//                            // 设置Hystrix参数
//                            // config.setSetter(xxx) // ②
//                        }))
//                        // 匹配路径
//                        .uri("http://localhost:6001"))
//                .build();
//    }


//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                // 设置请求路径满足ANT风格“/u/**”的路由
//                .route("user-service", r-> r.path("/u/**")
//                        // 通过StripPrefix过滤器添加响应参数
//                        .filters(f->f.stripPrefix(1)) // ①
//                        // 匹配路径
//                        .uri("http://localhost:6001"))
//                .build();
//    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                // 设置请求路径满足ANT风格“/u/**”的路由
//                .route("user-service", r-> r.path("/u/**")
//                        // 重写请求路径
//                        .filters(f->f.rewritePath("/u/(?<segment>.*)","/$\\{segment}"))//②
//                        // 匹配路径
//                        .uri("http://localhost:6001"))
//                .build();
//    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                // 设置请求路径满足ANT风格“/u/**”的路由
//                .route("hystrix-service", r-> r.path("/hystrix/**")
//                        // 设置状态（SetStatus）过滤器工厂设置状态
//                        .filters(f->f.setStatus(HttpStatus.BAD_REQUEST.value())) // ①
//                        // 匹配路径
//                        .uri("http://localhost:6001"))
//                .build();
//    }

    // 注入Resilience4j限流器注册机
    @Autowired
    private RateLimiterRegistry rateLimiterRegistry = null;

    /**
     * 限流逻辑
     * @return 是否放行结果
     */
    private ResultMessage rateLimit() {
        // 获取Resilience4j限速器
        RateLimiter userRateLimiter = rateLimiterRegistry.rateLimiter("user");
        // 绑定限速器
        Callable<ResultMessage> call
                = RateLimiter.decorateCallable(userRateLimiter,
                () -> new ResultMessage(true, "PASS") ); // ①
        // 尝试获取结果
        Try<ResultMessage> tryResult = Try.of(() -> call.call())
                // 降级逻辑
                .recover(ex -> new ResultMessage(false, "TOO MANY REQUESTS")); // ②
        ResultMessage result = tryResult.get();
        return result;
    }

//    /**
//     * 自定义Gateway过滤器
//     * @return Gateway过滤器
//     */
//    private GatewayFilter customGatewayFilter() {
//        return (exchange, chain) -> { // ①
//            // 执行Resilience4j限速器逻辑
//            ResultMessage resultMessage = rateLimit();
//            if (!resultMessage.getSuccess()) { // 不放行逻辑 ②
//                // 获取响应对象
//                ServerHttpResponse response = exchange.getResponse();
//                // 响应码为429——请求过多
//                response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
//                // 响应类型为JSON
//                response.getHeaders()
//                        .setContentType(MediaType.APPLICATION_JSON_UTF8);
//                // 转换为JSON字符串
//                String body =toJson(resultMessage);
//                // 响应数据放入缓冲区
//                DataBuffer dataBuffer
//                        = response.bufferFactory().wrap(body.getBytes());
//                // 使用限流结果响应请求，不再继续执行过滤器链
//                return response.writeWith(Mono.just(dataBuffer));
//            }
//            // 放行，继续执行过滤器链
//            return chain.filter(exchange); // ③
//        };
//    }

    /**
     *  将结果消息转换为JSON字符串
     * @param result -- 结果消息
     * @return JSON字符串
     */
    private String toJson(ResultMessage result) {
        ObjectMapper mapper = new ObjectMapper();
        String body = null;
        try {
            body = mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return body;
    }


//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                // 设置请求路径满足ANT风格“/user/**”的路由
//                .route("user-service", r-> r.path("/user/**")
//                        // 添加自定义过滤器
//                        .filters(f->f.filter(customGatewayFilter())) // ①
//                        // 匹配路径
//                        .uri("http://localhost:6001"))
//                .build();
//    }

//    @Bean // 装配为Spring Bean
//    public GlobalFilter tokenFilter() {
//        // Lambda表达式
//        return (exchange, chain) -> {
//            // 判定请求头token参数是否存在
//            boolean flag = !StringUtils.isBlank(
//                    exchange.getRequest().getHeaders().getFirst("token"));
//            if (flag) { // 存在，直接放行路由
//                return chain.filter(exchange);
//            }
//            // 获取token参数
//            String token = exchange.getRequest()
//                    .getQueryParams().getFirst("token");
//            ServerHttpRequest request = null;
//            // token参数不为空，路由时将它放入请求头
//            if (!StringUtils.isBlank(token)) {
//                request  = exchange.getRequest().mutate() // 构建请求头
//                        .header("token", token)
//                        .build();
//                // 构造请求头后执行责任链
//                return chain.filter(exchange.mutate().request(request).build());
//            }
//            // 放行
//            return chain.filter(exchange);
//        };
//    }

    @Bean
    public GatewayFilter customGatewayFilter() {
        int order = -100; // 排序
        // 创建委托过滤器
        GatewayFilter filter = (exchange, chain) -> {
            // 执行Resilience4j限速器逻辑
            ResultMessage resultMessage = rateLimit();
            if (!resultMessage.getSuccess()) { // 不放行
                ServerHttpRequest request = exchange.getRequest();
                // 获取响应对象
                ServerHttpResponse response = exchange.getResponse();
                // 响应码为429——请求过多
                response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                // 响应类型为JSON
                response.getHeaders()
                        .setContentType(MediaType.APPLICATION_JSON_UTF8);
                // 转换为JSON字符串
                String body =toJson(resultMessage);
                // 响应数据缓冲
                DataBuffer dataBuffer
                        = response.bufferFactory().wrap(body.getBytes());
                // 使用限流结果响应请求，不再继续执行过滤器链
                return exchange.getResponse().writeWith(Mono.just(dataBuffer));
            }
            // 放行，继续执行过滤器链
            return chain.filter(exchange);
        };
        // 创建OrderedGatewayFilter
        return new OrderedGatewayFilter(filter, order);
    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                // 设置请求路径满足ANT风格“/user/**”的路由
//                .route("user-service", r-> r.path("/user/**")
//                        // 使用服务发现的路由
//                        .uri("lb://user")) // ①
//                .build();
//    }
}
