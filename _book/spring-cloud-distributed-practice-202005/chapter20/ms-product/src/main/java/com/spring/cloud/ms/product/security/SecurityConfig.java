package com.spring.cloud.ms.product.security;


import com.spring.cloud.ms.common.token.TokenSecurityFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${secret.key}")
    private String secretKey = null; // 阴匙
    /**
     * 请求路径权限限制
     * @param http HTTP请求配置
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 创建Token过滤器
        TokenSecurityFilter tokenFilter = new TokenSecurityFilter(authenticationManager());
        // 设置阴匙
        tokenFilter.setSecretKey(secretKey);
        http.authorizeRequests()
            // 访问ANT风格“/user/**”需要USER或者ADMIN角色
            .antMatchers("/index.html").permitAll()
            .antMatchers("/actuator/**").permitAll()
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
