package com.spring.cloud.ms.fund.controller;

import com.spring.cloud.ms.common.pojo.ProductPojo;
import com.spring.cloud.ms.fund.facade.ProductFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FundController {

    @Autowired
    private ProductFacade productFacade = null;

    @GetMapping("/fund/product/{id}")
    public ProductPojo getProduct(@PathVariable("id") Long id) {
        return productFacade.getProduct(id);
    }
}
