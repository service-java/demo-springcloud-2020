package com.spring.cloud.mscommon.vo;

public class UserTokenBean {
    // 用户名称
    private String username = null;
    // 角色信息
    private String roles = null;

    /** setters and getters **/

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}