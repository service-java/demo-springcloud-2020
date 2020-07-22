package com.spring.cloud.security.service;


import com.spring.cloud.security.po.UserRolePo;

public interface UserService {
    public UserRolePo getUserRole(Long userId);

    public UserRolePo getUserRoleByUserName(String userName);
}
