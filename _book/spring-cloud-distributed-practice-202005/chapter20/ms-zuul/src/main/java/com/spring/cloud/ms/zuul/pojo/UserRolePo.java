package com.spring.cloud.ms.zuul.pojo;

import java.util.List;

public class UserRolePo {

    private UserPo user;
    private List<RolePo> roleList;

    public UserPo getUser() {
        return user;
    }

    public void setUser(UserPo user) {
        this.user = user;
    }

    public List<RolePo> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RolePo> roleList) {
        this.roleList = roleList;
    }
}
