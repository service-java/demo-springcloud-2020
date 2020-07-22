package com.spring.cloud.ms.zuul.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.spring.cloud.ms.common.key.DesCoderUtils;
import com.spring.cloud.ms.common.token.UserTokenBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Component
public class AuthRouterFilter extends ZuulFilter {
    // 3DES阴匙
    @Value("${secret.key}")
    private String secretKey = null;

    // 在路由之前执行
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    // 过滤器顺序
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 20;
    }

    // 判断是否需要拦截
    @Override
    public boolean shouldFilter() { // ③
        // 获取Spring Security登录信息
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null;
    }

    // 过滤器逻辑
    @Override
    public Object run() throws ZuulException {
        // 获取请求上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        // 获取当前用户认证信息
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        // 创建Token对象
        UserTokenBean tokenVo = initUserTokenVo(authentication);
        // 转发时间
        tokenVo.setIssueTime(new Date());
        // 半小时后超时
        long expireTime = System.currentTimeMillis()+ 1000*60*30;
        tokenVo.setExpireTime(new Date(expireTime));
        // 将对象转换为json字符串
        String json = JSON.toJSONString(tokenVo);
        // 通过3DES加密为密文
        String secretText = DesCoderUtils.encode3Des(secretKey, json);
        // 在路由前，放入请求头，等待下游服务器认证
        ctx.addZuulRequestHeader("token", secretText); // ④
        return null; // 放行
    }

    // 创建UserTokenBean对象
    private UserTokenBean initUserTokenVo(Authentication authentication) {
        // 用户名
        String username = authentication.getName();
        // 处理角色权限
        Collection authorities = authentication.getAuthorities();
        Iterator iterator = authorities.iterator();
        StringBuilder roles = new StringBuilder("");
        while(iterator.hasNext()) {
            GrantedAuthority authority = (GrantedAuthority) iterator.next();
            roles.append("," + authority.getAuthority());
        }
        roles.delete(0, 1);
        // 创建token对象，并设值
        UserTokenBean tokenVo = new UserTokenBean();
        tokenVo.setUsername(username);
        tokenVo.setRoles(roles.toString());
        return tokenVo;
    }
}
