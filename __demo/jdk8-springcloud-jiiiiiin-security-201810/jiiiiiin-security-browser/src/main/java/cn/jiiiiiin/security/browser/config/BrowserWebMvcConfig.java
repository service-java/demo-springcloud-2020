package cn.jiiiiiin.security.browser.config;

import cn.jiiiiiin.security.core.dict.SecurityConstants;
import cn.jiiiiiin.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author jiiiiiin
 */
@Configuration
public class BrowserWebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        if (securityProperties.getBrowser().getSignInUrl().equals(SecurityConstants.DEFAULT_SIGN_IN_PAGE_URL)) {
            // setViewName("signIn")指向`/jiiiiiin-security-browser/src/main/resources/templates/signIn.html`
            registry.addViewController(securityProperties.getBrowser().getSignInUrl()).setViewName(securityProperties.getBrowser().getSignInUrl().substring(1));
        }
        if (securityProperties.getBrowser().getSession().getSessionInvalidUrl().equals(SecurityConstants.DEFAULT_SESSION_INVALID_URL)) {
            registry.addViewController(securityProperties.getBrowser().getSession().getSessionInvalidUrl()).setViewName(securityProperties.getBrowser().getSession().getSessionInvalidUrl().substring(1));
        }
    }

}
