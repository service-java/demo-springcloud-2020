package com.forezp.client.hystrix;

import com.forezp.client.UserServiceClient;
import com.forezp.dto.ResponseVO;
import com.forezp.entity.User;
import org.springframework.stereotype.Component;


/**
 * Created by fangzhipeng on 2017/5/31.
 */
@Component
public class UserServiceHystrix implements UserServiceClient {

    @Override
    public ResponseVO<User> getUser(String token, String username) {
        System.out.println(token);
        System.out.println(username);
        return null;
    }
}
