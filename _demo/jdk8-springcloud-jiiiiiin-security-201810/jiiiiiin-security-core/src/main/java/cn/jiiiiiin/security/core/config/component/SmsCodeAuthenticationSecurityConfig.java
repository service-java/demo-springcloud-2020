/**
 *
 */
package cn.jiiiiiin.security.core.config.component;

import cn.jiiiiiin.security.core.authentication.mobile.SmsCodeAuthenticationFilter;
import cn.jiiiiiin.security.core.authentication.mobile.SmsCodeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 短信登录配置
 * <p>
 * 将短信验证码过滤器添加到ss框架的过滤器链中
 * <p>
 * 需要将当前组件添加到对应的ss框架的认证配置中，相当于在自定义配置中添加当前的针对短信验证码的公共配置
 *
 * @author zhailiang
 * @author jiiiiiin
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    /* (non-Javadoc)
     * @see org.springframework.security.config.annotation.SecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.SecurityBuilder)
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {

        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
        // 注入认证管理器
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        // 注入认证成功处理器
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        // 注入认证失败处理器
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        String key = UUID.randomUUID().toString();
        smsCodeAuthenticationFilter.setRememberMeServices(new PersistentTokenBasedRememberMeServices(key, userDetailsService, persistentTokenRepository));

        // 配置支持认证的provider
        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        // 配置provider所需的user details service
        smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);
        http
                // 将自定义provider添加到authentication manager托管集合中
                .authenticationProvider(smsCodeAuthenticationProvider)
                // 将自定义authentication filter设置到sp过滤器链
                .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
