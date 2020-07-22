package com.spring.cloud.msclient1.controller;

import com.spring.cloud.msclient1.feign.FeignTestFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**** imports ****/
@RestController
@RequestMapping("/test")
public class TestController {
    // OpenFeign接口
    @Autowired
    private FeignTestFacade feignTestFacade = null;

    // 测试OpenFeign接口
    @GetMapping("/feign")
    public String feign() {
        return feignTestFacade.feignTest();
    }

    // 测试OpenFeign接口性能
    @GetMapping("/feign2")
    public String feign2() {
        int count = 0;
        long start = System.currentTimeMillis();
        while(true) {
            long current = System.currentTimeMillis();
            // 限制在100毫秒内调用
            if (current - start <=100) {
                // 服务调用
                feignTestFacade.feignTest();
                count ++;
            } else { // 退出循环
                break;
            }
        }
        return "100毫秒调用" +count + "次";
    }
}