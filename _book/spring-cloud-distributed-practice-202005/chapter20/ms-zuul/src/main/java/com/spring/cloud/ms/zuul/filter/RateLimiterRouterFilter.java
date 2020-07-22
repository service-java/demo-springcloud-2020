package com.spring.cloud.ms.zuul.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.spring.cloud.ms.common.vo.SuccessOrFailureMessage;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import org.apache.http.HttpStatus;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

// 限流过滤器
@Component
public class RateLimiterRouterFilter extends ZuulFilter {

    // 限速器注册机
    @Autowired
    private RateLimiterRegistry rateLimiterRegistry = null;

    // 路由之前执行
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    // 拦截器的顺序
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 10;
    }

    // 判断是否执行拦截器
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
         // 从限速器注册机中获取“common”限速器
        RateLimiter commonRateLimiter
            = rateLimiterRegistry.rateLimiter("common");
        // 描述事件
        CheckedFunction0<SuccessOrFailureMessage> decoratedSupplier =
            RateLimiter.decorateCheckedSupplier(
                commonRateLimiter,
                ()->new SuccessOrFailureMessage(true, "通过"));
        // 发送事件
        Try<SuccessOrFailureMessage> result = Try.of(decoratedSupplier)
            // 如果发生异常，则执行降级方法
            .recover(ex -> {
                return new SuccessOrFailureMessage(false, "当前流量过大，请稍后再试。");
            });
        // 返回结果
        SuccessOrFailureMessage message = result.get();
        if (message.isSuccess()) { // 在流量范围之内
            return null;
        } else { // 流量过大
            RequestContext ctx = RequestContext.getCurrentContext();
            // 不再放行路由，逻辑到此为止
            ctx.setSendZuulResponse(false);
            // 设置响应码为421-太多请求
            ctx.setResponseStatusCode(421);
            // 设置响应类型
            ctx.getResponse()
                    .setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            // 将result转换为JSON字符串
            ObjectMapper mapper = new ObjectMapper();
            String body = null;
            try {
                body = mapper.writeValueAsString(message); // 转变为JSON字符串
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            // 设置响应体
            ctx.setResponseBody(body);
            return null;
        }
    }
}
