package com.spring.cloud.ms.zuul.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
// 继承WebSecurityConfigurerAdapter
public class SecurityConfigure extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder = null;

    // 用户认证服务
    @Autowired
    private UserDetailsService userDetailsService = null;

    @Bean("passwordEncoder")
    public PasswordEncoder initPasswordEncoder() { // 密码编码器
        if (passwordEncoder == null) {
            passwordEncoder = new BCryptPasswordEncoder();
        }
        return passwordEncoder;
    }

    /**
     * 用户认证
     * @param auth 认证构建
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        // 设置自定用户认证服务和密码编码器
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(this.initPasswordEncoder());
    }

    /**
     * 请求路径权限限制
     * @param http HTTP请求配置
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            // 访问ANT风格“/user/**”需要USER或者ADMIN角色
            .antMatchers("/index.html").permitAll()
            // 开放Actuator端点权限，给Spring Boot Admin监控
            .antMatchers("/actuator/**").permitAll()
            // 无权限配置的全部开放给已经登录的用户
            .anyRequest().authenticated()
            // 使用页面登录
            .and().formLogin();
    }


}
