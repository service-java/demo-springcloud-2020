package com.spring.cloud.ms.fund.facade;

import com.spring.cloud.ms.common.pojo.ProductPojo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// OpenFeign接口声明
@FeignClient("ms-product")
public interface ProductFacade {

    // 定义服务调用接口，Spring MVC方式声明
    @GetMapping("/product/{id}")
    public ProductPojo getProduct(@PathVariable("id") Long id);
}
