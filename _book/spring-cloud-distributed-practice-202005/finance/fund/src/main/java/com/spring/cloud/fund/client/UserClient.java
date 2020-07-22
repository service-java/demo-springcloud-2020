package com.spring.cloud.fund.client;

import com.spring.cloud.fund.facade.UserFacade;
import org.springframework.cloud.openfeign.FeignClient;

//@FeignClient(value="user")
public interface  UserClient extends UserFacade {
}
