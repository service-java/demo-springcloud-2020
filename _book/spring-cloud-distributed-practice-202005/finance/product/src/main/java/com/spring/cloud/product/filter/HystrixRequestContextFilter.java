package com.spring.cloud.product.filter;
/**** imports ****/
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

// @WebFilter表示Servlet过滤器，
@WebFilter(filterName ="HystrixRequestContextFilter",
        //urlPatterns定义拦截的地址
        urlPatterns ="/user/info/cache/*")
@Component // 表示被Spring扫描，装配为Servlet过滤器
public class HystrixRequestContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        // 初始化上下文
        HystrixRequestContext context
                = HystrixRequestContext.initializeContext();
        try {
            chain.doFilter(request, response);
        } finally {
            // 关闭上下文
            context.shutdown();
        }
    }
}