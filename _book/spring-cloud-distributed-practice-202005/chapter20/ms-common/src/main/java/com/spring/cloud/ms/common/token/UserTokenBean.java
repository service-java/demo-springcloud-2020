package com.spring.cloud.ms.common.token;

import java.io.Serializable;
import java.util.Date;

public class UserTokenBean implements Serializable  {

    public static  final long serialVersionUID = 1221873465L;

    // 用户名称
    private String username = null;
    // 角色信息
    private String roles = null;

    // 签发时间
    private Date issueTime = null;
    // 超时时间
    private Date expireTime = null;

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

    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
