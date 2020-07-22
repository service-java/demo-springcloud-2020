package com.spring.cloud.sleuth.zuul.filter;

import brave.Tracer;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**** imports ****/
@Component
public class TraceFilter extends ZuulFilter {

    private static final Logger logger
            = LoggerFactory.getLogger(TraceFilter.class);

    // 注入Brave的Tracer对象
    @Autowired
    private Tracer tracer = null; // ①

    @Override
    public Object run() throws ZuulException {
        // 添加一个span的属性标记
        tracer.currentSpan().tag("1001", "GET请求");
        // 当前trace id
        String traceId = tracer.currentSpan().context().traceIdString();
        // 当前 span id
        String spanId = tracer.currentSpan().context().spanIdString();
        // 日志打印
        logger.info("当前追踪参数：traceId={}，spanId={}", traceId, spanId);
        return null;
    }

    @Override
    public boolean shouldFilter() {
        // 获取请求上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        // 判断是否GET请求
        return "GET".equalsIgnoreCase(ctx.getRequest().getMethod());
    }

    // 过滤器顺序
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 10;
    }

    // 过滤器类型为“pre”
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }
}