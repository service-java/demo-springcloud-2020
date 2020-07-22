package com.spring.cloud.gateway.main;

import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class RoutesMain {

    public static void main(String[] args) {
        deleteRoutes();
    }

    public static void saveRoutes() {
        // REST风格请求模板
        RestTemplate restTemplate = new RestTemplate();
        // 需要保持的路由配置
        RouteDefinition routeDefinition = routeDefinition();
        // 包装请求体
        HttpEntity<RouteDefinition> request = new HttpEntity<>(routeDefinition);
        // 请求路径
        String url = "http://localhost:3001/actuator/gateway/routes/hystrix";
        // POST请求
        ResponseEntity<Void> obj
                = restTemplate.postForEntity(url, request, Void.class);
    }

    private static RouteDefinition routeDefinition() {
        // 路由定义
        RouteDefinition routeDefinition = new RouteDefinition();
        // 设置路由编号
        routeDefinition.setId("hystrix");
        // 路由URI
        routeDefinition.setUri(URI.create("lb://USER"));
        // 路由顺序
        routeDefinition.setOrder(1000);
        // 定义断言
        PredicateDefinition predicateDefinition
                = new PredicateDefinition("Path=/hystrix/**");
        List<PredicateDefinition> predicates = new ArrayList<>();
        predicates.add(predicateDefinition);
        routeDefinition.setPredicates(predicates);
        // 定义过滤器
        FilterDefinition filterDefinition
                = new FilterDefinition("AddRequestHeader=id, 1");
        List<FilterDefinition> filters = new ArrayList<>();
        filters.add(filterDefinition);
        routeDefinition.setFilters(filters);
        // 返回路由定义
        return routeDefinition;
    }

    public static void deleteRoutes() {
        // REST风格请求模板
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:3001/actuator/gateway/routes/hystrix";
        // DELETE方法，删除路由
        restTemplate.delete(url);
    }
}
