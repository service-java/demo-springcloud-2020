package com.spring.cloud.ms.zuul.service;


import com.spring.cloud.ms.zuul.pojo.UserRolePo;

public interface UserService {

    public UserRolePo getUserRole(Long userId);

    public UserRolePo getUserRoleByUserName(String userName);
}
