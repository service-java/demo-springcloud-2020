package com.spring.cloud.security.service.impl;


import com.spring.cloud.security.po.RolePo;
import com.spring.cloud.security.po.UserPo;
import com.spring.cloud.security.po.UserRolePo;
import com.spring.cloud.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService { // ①

    // 注入UserService对象
    @Autowired
    private UserService userService = null;

    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        // 获取用户角色信息
        UserRolePo userRole = userService.getUserRoleByUserName(userName);
        // 转换为Spring Security用户详情
        return change(userRole);
    }

    private UserDetails change(UserRolePo userRole) {
        // 权限列表
        List<GrantedAuthority> authorityList = new ArrayList<>();
        // 获取用户角色信息
        List<RolePo> roleList = userRole.getRoleList();
        // 将角色名称放入权限列表中
        for (RolePo role: roleList) {
            GrantedAuthority authority
                    = new SimpleGrantedAuthority(role.getRoleName());
            authorityList.add(authority);
        }
        UserPo user = userRole.getUser(); // 用户信息
        // 创建Spring Security用户详情
        UserDetails result // ②
                = new User(user.getUserName(),
                user.getPassword(), authorityList);
        return result;
    }
}
