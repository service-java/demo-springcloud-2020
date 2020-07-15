/**
 *
 */
package cn.jiiiiiin.security.core.authorize;

import cn.jiiiiiin.security.core.dict.SecurityConstants;
import cn.jiiiiiin.security.core.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 核心模块的授权配置提供器，安全模块涉及的url的授权配置在这里。
 *
 * @author zhailiang
 * @see HttpSecurity#authorizeRequests()
 */
@Component
@Order(Integer.MIN_VALUE)
public class CustomAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM,
                        SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,
                        SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_OPENID,
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                        SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL,
                        securityProperties.getBrowser().getSignInUrl(),
                        securityProperties.getBrowser().getSignUpUrl(),
                        securityProperties.getBrowser().getSession().getSessionInvalidUrl())
                .permitAll();

        if (StringUtils.isNotBlank(securityProperties.getBrowser().getSignOutUrl())) {
            config.antMatchers(securityProperties.getBrowser().getSignOutUrl()).permitAll();
        }
        return false;
    }

}
