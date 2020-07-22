package com.spring.cloud.gateway.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**** imports ****/
@Component // 让Spring IoC容器扫描 ①
public class TokenFilter implements GlobalFilter, Ordered { // ②
    // 过滤器逻辑
    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange, GatewayFilterChain chain) {
        // 判定请求头token参数是否存在
        boolean flag = !StringUtils.isBlank(
                exchange.getRequest().getHeaders().getFirst("token"));
        if (flag) { // 存在，直接放行路由
            return chain.filter(exchange);
        }
        // 获取auth-token参数
        String token = exchange.getRequest()
                .getQueryParams().getFirst("token");
        ServerHttpRequest request = null;
        // token参数不为空，路由时将它放入请求头
        if (!StringUtils.isBlank(token)) {
            request  = exchange.getRequest().mutate()
                    .header("token", token)
                    .build();
            // 构造请求头后执行责任链
            return chain.filter(exchange.mutate().request(request).build());
        }
        // 放行
        return chain.filter(exchange);
    }

    // 排序  ③
    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 100001;
    }
}