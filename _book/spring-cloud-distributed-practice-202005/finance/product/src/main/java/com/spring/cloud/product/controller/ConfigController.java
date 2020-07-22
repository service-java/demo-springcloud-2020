package com.spring.cloud.product.controller;

import com.spring.cloud.common.pojo.UserInfo;
import com.spring.cloud.product.facade.ConfigFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/config")
@RestController
public class ConfigController {

    @Autowired
    private ConfigFacade configFacade = null;

    @GetMapping("/cb/{id}")
    public UserInfo getUserWithCircuitBreaker(@PathVariable("id") Long id) {
        return configFacade.getUserWithCircuitBreaker(id);
    }

    @GetMapping("/rl/{id}")
    public UserInfo getUserWithRatelimiter(@PathVariable("id") Long id) {
        return configFacade.getUserWithRatelimiter(id);
    }
}
