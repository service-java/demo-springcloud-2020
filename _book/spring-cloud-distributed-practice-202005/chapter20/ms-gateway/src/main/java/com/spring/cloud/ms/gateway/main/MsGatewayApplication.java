package com.spring.cloud.ms.gateway.main;

import com.alibaba.fastjson.JSONObject;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.Callable;

@SpringBootApplication(scanBasePackages = "com.spring.cloud.ms.gateway")

public class MsGatewayApplication {

    // 注入Resilience4j限流器注册机
    @Autowired
    private RateLimiterRegistry rateLimiterRegistry = null; // ①

    // 创建全局过滤器
    @Bean("limitGlobalFilter")
    public GlobalFilter limitGlobalFilter() {
        // Lambda表达式
        return (exchange, chain) -> {
            // 获取Resilience4j限速器
            RateLimiter userRateLimiter
                    = rateLimiterRegistry.rateLimiter("commonLimiter");
            // 绑定限速器
            Callable<ResultMessage> call // ②
                    = RateLimiter.decorateCallable(userRateLimiter,
                    () -> new ResultMessage(true, "PASS") );
            // 尝试获取结果
            Try<ResultMessage> tryResult = Try.of(() -> call.call()) // ③
                    // 降级逻辑
                    .recover(ex -> new ResultMessage(false, "TOO MANY REQUESTS"));
            // 获取请求结果
            ResultMessage result = tryResult.get();
            if (result.isSuccess()) { // 没有超过流量
                // 执行下层过滤器
                return chain.filter(exchange);
            } else { // 超过流量
                // 响应对象
                ServerHttpResponse serverHttpResponse = exchange.getResponse();
                // 设置响应码
                serverHttpResponse.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                // 转换为JSON字节
                byte[] bytes = JSONObject.toJSONString(result).getBytes();
                DataBuffer buffer
                        = exchange.getResponse().bufferFactory().wrap(bytes);
                // 响应体，提示请求超流量
                return serverHttpResponse.writeWith(Flux.just(buffer));
            }
        };
    }


    class ResultMessage {
        // 通过成功标志
        private boolean success;
        // 信息
        private String note;

        public ResultMessage() {
        }

        public ResultMessage(boolean success, String note) {
            this.success = success;
            this.note = note;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }
    }

    @Autowired // 注入StringRedisTemplate对象
    private StringRedisTemplate stringRedisTemplate = null;

    @Bean(name = "blacklistFilter")
    public GlobalFilter blacklistFilter() {
        return (exchange, chain) -> {
            String username = exchange.getRequest(). // 获取请求参数
                    getQueryParams().getFirst("username");
            // 如果参数为空，则不执行过滤逻辑
            if (StringUtils.isEmpty(username)) {
                return chain.filter(exchange);
            }
            String value = (String)stringRedisTemplate.opsForHash()
                    .get("blacklist", username); // 获取黑名单用户信息
            // 不存在或者标志为0，则为正常用户，放行
            if (StringUtils.isEmpty(value) || "0".equals(value)) {
                return chain.filter(exchange);
            } else { // 是黑名单用户，则拦截请求
                // 响应对象
                ServerHttpResponse serverHttpResponse = exchange.getResponse();
                // 设置响应码（禁止请求）
                serverHttpResponse.setStatusCode(HttpStatus.FORBIDDEN);
                ResultMessage result
                        = new ResultMessage(false, "黑名单用户，请联系客服处理");
                // 转换为JSON字节
                byte[] bytes = JSONObject.toJSONString(result).getBytes();
                DataBuffer buffer
                        = exchange.getResponse().bufferFactory().wrap(bytes);
                // 响应体，提示请求黑名单用户，禁止请求
                return serverHttpResponse.writeWith(Mono.just(buffer));
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(MsGatewayApplication.class, args);
    }

}
