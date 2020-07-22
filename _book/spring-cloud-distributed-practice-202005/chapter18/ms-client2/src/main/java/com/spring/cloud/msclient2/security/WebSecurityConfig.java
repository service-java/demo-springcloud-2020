package com.spring.cloud.msclient2.security;

import com.spring.cloud.mscommon.filter.TokenFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**** imports ****/
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${secret.key}")
    private String secretKey = null; // 阴匙

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 创建Token过滤器
        TokenFilter tokenFilter = new TokenFilter(authenticationManager());
        // 设置阴匙
        tokenFilter.setSecretKey(secretKey);
        http
                .authorizeRequests()
                // 所有请求访问需要签名
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                // 在责任链上添加过滤器
                .addFilterBefore(tokenFilter,
                        UsernamePasswordAuthenticationFilter.class);
    }
}