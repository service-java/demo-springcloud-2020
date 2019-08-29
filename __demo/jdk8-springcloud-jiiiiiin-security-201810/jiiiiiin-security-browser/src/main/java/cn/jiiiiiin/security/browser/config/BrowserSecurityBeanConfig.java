/**
 *
 */
package cn.jiiiiiin.security.browser.config;

import cn.jiiiiiin.security.browser.component.authentication.BrowserAuthenticationFailureHandler;
import cn.jiiiiiin.security.browser.component.authentication.BrowserAuthenticationSuccessHandler;
import cn.jiiiiiin.security.browser.component.authentication.BrowserLoginUrlAuthenticationEntryPoint;
import cn.jiiiiiin.security.browser.logout.BrowserLogoutSuccessHandler;
import cn.jiiiiiin.security.browser.session.CustomExpiredSessionStrategy;
import cn.jiiiiiin.security.browser.session.CustomInvalidSessionStrategy;
import cn.jiiiiiin.security.core.dict.SecurityConstants;
import cn.jiiiiiin.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 浏览器环境下扩展点配置，配置在这里的bean，业务系统都可以通过声明同类型或同名的bean来覆盖安全
 * 模块默认的配置。
 *
 * @author zhailiang
 * @author jiiiiiin
 */
@Configuration
public class BrowserSecurityBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * session失效时的处理策略配置
     *
     * @return
     * @see HttpSecurity#sessionManagement() {@link #invalidSessionStrategy()}
     */
    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new CustomInvalidSessionStrategy(securityProperties);
    }

    /**
     * 并发登录导致前一个session失效时的处理策略配置
     *
     * @return
     * @see HttpSecurity#sessionManagement() {@link #sessionInformationExpiredStrategy()}
     */
    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new CustomExpiredSessionStrategy(securityProperties);
    }

    /**
     * 退出时的处理策略配置
     * 授权配置之退出成功处理器配置
     *
     * @return
     * @see HttpSecurity#logout() {@link #logoutSuccessHandler()}
     */
    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new BrowserLogoutSuccessHandler(securityProperties.getBrowser().getSignOutUrl());
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationEntryPoint.class)
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new BrowserLoginUrlAuthenticationEntryPoint(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL);
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationSuccessHandler.class)
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new BrowserAuthenticationSuccessHandler();
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationFailureHandler.class)
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new BrowserAuthenticationFailureHandler();
    }

}
