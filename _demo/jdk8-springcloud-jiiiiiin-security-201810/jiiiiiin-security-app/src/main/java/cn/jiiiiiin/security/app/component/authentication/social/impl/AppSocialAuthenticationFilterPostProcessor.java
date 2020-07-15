package cn.jiiiiiin.security.app.component.authentication.social.impl;

import cn.jiiiiiin.security.core.social.support.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author jiiiiiin
 */
@Component
public class AppSocialAuthenticationFilterPostProcessor implements SocialAuthenticationFilterPostProcessor {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    public void process(SocialAuthenticationFilter socialAuthenticationFilter) {
        // 修改spring social的默认授权后处理
        socialAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
    }
}
