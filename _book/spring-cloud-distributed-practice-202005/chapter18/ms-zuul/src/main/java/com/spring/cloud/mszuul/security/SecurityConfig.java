package com.spring.cloud.mszuul.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**** imports ****/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 编码器
    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 用户认证
     * @param auth 认证构建
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication() // 使用内存保存验证信息
                .passwordEncoder(encoder). // 设置编码器
                // 设置用户名、密码和角色
                        withUser("admin").password(encodePwd("abcdefg"))
                // roles方法Spring Security中加入前缀“ROLE_”
                .roles("ADMIN", "USER") // 赋予两个角色

                .and()
                // 创建第二个用户
                .withUser("user").password(encodePwd("123456789"))
                .roles("USER"); // 赋予一个角色
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
                // 无权限配置的全部开放给已经登录的用户
                .anyRequest().authenticated()
                // 使用页面登录
                .and().formLogin();
    }

    // 对密码进行加密
    private String encodePwd(String pwd) {
        return encoder.encode(pwd);
    }
}