package com.spring.cloud.sso.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**** imports ****/

@Configuration
public class SsoSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder = null; // 密码编码器

    /**
     * 配置登录方式等
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception { // ②
        http
            .csrf().disable() // 禁用CSRF
            .formLogin() // 表单登录
            .and().authorizeRequests() // 所有请求都需要认证
            .anyRequest().authenticated();
    }

    /**
     * 配置用户和对应的权限
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
                throws Exception { // ③
        auth.inMemoryAuthentication()
            // 创建用户“admin”，设置密码并赋予角色
            .withUser("admin").password(passwordEncoder.encode("admin"))
                .roles("ADMIN", "USER")
            .and()
            // 创建用户“admin”，设置密码并赋予角色
            .withUser("user").password(passwordEncoder.encode("user"))
                .roles("USER");
    }
}