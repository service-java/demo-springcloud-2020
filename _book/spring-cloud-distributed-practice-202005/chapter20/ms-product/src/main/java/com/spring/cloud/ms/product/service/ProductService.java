package com.spring.cloud.ms.product.service;


import com.spring.cloud.ms.common.pojo.ProductPojo;

public interface ProductService {

    public ProductPojo getProduct(Long id);

    public ProductPojo getLatestProduct(Long id);

    public Integer reduceStock(Long xid, Integer quantity, Long id);


}
