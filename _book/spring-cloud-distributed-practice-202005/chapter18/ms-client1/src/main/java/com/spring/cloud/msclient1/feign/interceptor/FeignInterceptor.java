package com.spring.cloud.msclient1.feign.interceptor;

import com.spring.cloud.mscommon.thread.TokenThreadLocal;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**** imports ****/
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 获取线程变量
        String token = TokenThreadLocal.get().getToken();
        // 在调用时，将认证信息添加到请求头中
        requestTemplate.header("token", token);
    }
}