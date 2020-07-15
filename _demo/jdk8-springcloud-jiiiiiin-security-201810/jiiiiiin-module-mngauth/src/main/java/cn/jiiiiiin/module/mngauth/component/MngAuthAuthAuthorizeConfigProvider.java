/**
 *
 */
package cn.jiiiiiin.module.mngauth.component;

import cn.jiiiiiin.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author jiiiiiin
 */
@Component
public class MngAuthAuthAuthorizeConfigProvider implements AuthorizeConfigProvider {

    /* (non-Javadoc)
     * @see com.imooc.security.core.authorize.AuthorizeConfigProvider#config(org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry)
     */
    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config
                .antMatchers("/admin").hasRole("ADMIN");
        return false;
    }

}
