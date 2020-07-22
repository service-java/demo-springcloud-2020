package com.spring.cloud.product.facade;

import com.spring.cloud.common.pojo.UserInfo;

public interface ConfigFacade {
    public UserInfo getUserWithCircuitBreaker(Long id);

    public UserInfo getUserWithRatelimiter(Long id);
}
