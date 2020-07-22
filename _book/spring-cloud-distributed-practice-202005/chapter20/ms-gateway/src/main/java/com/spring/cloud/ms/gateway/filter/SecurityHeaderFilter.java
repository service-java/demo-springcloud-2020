package com.spring.cloud.ms.gateway.filter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.time.Duration;

@Configuration
public class SecurityHeaderFilter implements GlobalFilter, ApplicationContextAware {


    private ApplicationContext applicationContext = null;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        Mono<UserDetails>  userDetailsMono = ReactiveSecurityContextHolder.getContext()
//                .filter(c -> c.getAuthentication() != null)
//                .map(SecurityContext::getAuthentication)
//                .map(Authentication::getPrincipal)
//                .cast(UserDetails.class);
//        Principal p = exchange.getPrincipal().cache().block(Duration.ofMillis(1000));

        String []names  = applicationContext.getBeanDefinitionNames();
        Authentication auth = null;
        for (String name: names) {
            Object bean = applicationContext.getBean(name);
            if (bean instanceof  Authentication) {
                auth = (Authentication) bean;
            }
        }
        if (auth != null) {
            auth.getName();
        }
        return chain.filter(exchange);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
