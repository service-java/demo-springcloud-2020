package com.spring.cloud.ms.fund.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.spring.cloud.ms.common.pojo.ProductPojo;
import com.spring.cloud.ms.fund.facade.ProductFacade;
import com.spring.cloud.ms.fund.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;

public class FundServiceImpl implements FundService {

    @Autowired
    private ProductFacade productFacade = null;

    @Override
    // 使用Hystrix命令进行服务调用，并设置降级方法
    @HystrixCommand(fallbackMethod = "fallback")
    public ProductPojo getProduct(Long id) {
        return productFacade.getProduct(id);
    }

    // 服务调用
    public void fallback(Long id, Exception ex) {
        throw new RuntimeException("获取产品编号为【"+id+"】失败", ex);
    }
}
