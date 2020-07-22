package com.spring.cloud.ms.zuul.service.impl;

import com.spring.cloud.ms.zuul.dao.RoleDao;
import com.spring.cloud.ms.zuul.dao.UserDao;
import com.spring.cloud.ms.zuul.pojo.RolePo;
import com.spring.cloud.ms.zuul.pojo.UserPo;
import com.spring.cloud.ms.zuul.pojo.UserRolePo;
import com.spring.cloud.ms.zuul.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleDao roleDao = null;

    @Autowired
    private UserDao userDao = null;

    // 根据用户编号找到用户角色信息
    @Override
    @Transactional
    public UserRolePo getUserRole(Long userId) {
        UserRolePo userRole = new UserRolePo();
        // 获取用户信息
        UserPo user = userDao.getUser(userId);
        // 获取用户角色信息
        List<RolePo> roleList =  roleDao.findRolesByUserId(userId);
        userRole.setUser(user);
        userRole.setRoleList(roleList);
        return userRole;
    }

    // 根据用户名称找到用户角色信息
    @Override
    public UserRolePo getUserRoleByUserName(String userName) {
        UserRolePo userRole = new UserRolePo();
        // 获取用户信息
        UserPo user = userDao.getUserByUserName(userName);
        // 获取用户角色信息
        List<RolePo> roleList =  roleDao.findRolesByUserId(user.getId());
        userRole.setUser(user);
        userRole.setRoleList(roleList);
        return userRole;
    }

}
