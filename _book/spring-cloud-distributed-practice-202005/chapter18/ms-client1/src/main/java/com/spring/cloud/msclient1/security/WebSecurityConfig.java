package com.spring.cloud.msclient1.security;

import com.spring.cloud.mscommon.filter.TokenFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**** imports ****/
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    // 3DES阴匙
    @Value("${secret.key}")
    private String secretKey = null;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        TokenFilter tokenFilter = new TokenFilter(authenticationManager());
        tokenFilter.setSecretKey(secretKey);
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/index.html").permitAll()
                // 其余请求访问受限
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                //token interceptor 根据header 生成认证信息
                .addFilterBefore(tokenFilter,
                        UsernamePasswordAuthenticationFilter.class);
    }
}