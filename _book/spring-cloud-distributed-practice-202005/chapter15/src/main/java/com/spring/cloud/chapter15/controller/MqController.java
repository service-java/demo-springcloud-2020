package com.spring.cloud.chapter15.controller;

import com.spring.cloud.chapter15.product.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mq")
public class MqController {

    @Autowired
    private PurchaseService purchaseService = null;

    @GetMapping("/test")
    public String testMq() {
        return purchaseService.purchase(1L, 1L, 200.0) + "";
    }
}
