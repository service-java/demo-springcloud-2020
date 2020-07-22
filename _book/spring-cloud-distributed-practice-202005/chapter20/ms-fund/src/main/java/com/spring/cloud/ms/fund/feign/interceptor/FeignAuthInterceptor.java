package com.spring.cloud.ms.fund.feign.interceptor;

import com.spring.cloud.ms.common.token.TokenThreadLocal;
import feign.RequestInterceptor;
import feign.RequestTemplate;

// 继承RequestInterceptor
public class FeignAuthInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 获取线程变量
        String token = TokenThreadLocal.get().getToken();
        // 在调用时，将认证信息添加到请求头中
        requestTemplate.header("token", token);
    }
}
