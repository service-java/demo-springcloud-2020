package com.spring.cloud.mscommon.filter;

import com.alibaba.fastjson.JSON;
import com.spring.cloud.mscommon.code.DesCoderUtils;
import com.spring.cloud.mscommon.thread.TokenThreadLocal;
import com.spring.cloud.mscommon.vo.UserTokenBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**** imports ****/
public class TokenFilter extends BasicAuthenticationFilter {// ①
    // 阴匙
    private String secretKey = null;

    // 构造方法，会调用父类方法
    public TokenFilter(AuthenticationManager authenticationManager) { // ②
        super(authenticationManager);
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * 连接器方法
     * @param request HTTP请求
     * @param response HTTP响应
     * @param chain 过滤器责任链
     * @throws IOException IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 从请求头（header）中提取token
        String token = request.getHeader("token");
        try {
            if (!StringUtils.isEmpty(token)) { // token不为空
                // 解密token
                String tokenText = DesCoderUtils.decode3Des(secretKey, token);
                // 转换为Java对象
                UserTokenBean userVo
                        = JSON.parseObject(tokenText, UserTokenBean.class);
                // 获取认证信息
                Authentication authentication = createAuthentication(userVo);
                // 保存token到线程变量
                TokenThreadLocal.get().setToken(token);
                // 设置认证信息，完成认证
                SecurityContextHolder
                        .getContext().setAuthentication(authentication); // ③
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 执行下级过滤器
            chain.doFilter(request, response);
        }
    }

    /**
     *
     * @param userVo token信息
     * @return
     */
    private Authentication createAuthentication(UserTokenBean userVo) {
        // 创建认证Principal对象
        Principal principal = () -> userVo.getUsername();
        // 角色信息
        String roles = userVo.getRoles();
        String[] roleNames = (roles == null ? new String[0] : roles.split(","));
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (String roleName : roleNames) {
            // 创建授予权限信息对象
            GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
            authorityList.add(authority);
        }
        // 创建认证信息，它是Authentication的实现类
        return new UsernamePasswordAuthenticationToken(
                principal, "", authorityList);
    }
}