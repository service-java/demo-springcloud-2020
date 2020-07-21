package com.forezp.client;

import com.forezp.client.hystrix.UserServiceHystrix;
import com.forezp.dto.ResponseVO;
import com.forezp.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;



/**
 * Created by fangzhipeng on 2017/5/27.
 */

@FeignClient(value = "user-service",fallback = UserServiceHystrix.class )
public interface UserServiceClient {

    @PostMapping(value = "/user/{username}")
    ResponseVO<User> getUser(@RequestHeader(value = "Authorization") String token, @PathVariable("username") String username);
}



