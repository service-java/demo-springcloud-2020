package com.spring.cloud.ms.fund.service;

import com.spring.cloud.ms.common.pojo.ProductPojo;

public interface FundService {

    public ProductPojo getProduct(Long id);
}
