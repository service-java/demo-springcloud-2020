package com.spring.cloud.chapter19.thrift.service.impl;

import com.spring.cloud.chapter19.thrift.pojo.UserPojo;
import com.spring.cloud.chapter19.thrift.service.UserService;
import org.apache.thrift.TException;

/**** imports ****/

// 实现UserService.Iface接口
public class UserServiceImpl implements UserService.Iface {

    // 实现接口定义的方法
    @Override
    public UserPojo getUser(long id) throws TException {
        UserPojo user = new UserPojo();
        user.setId(id);
        user.setUserName("user_name_" + id);
        user.setNote("note_" + id);
        return user;
    }
}
