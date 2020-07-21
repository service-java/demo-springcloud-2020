/**
 *
 */
package cn.jiiiiiin.security.rbac.component.authorize;

import cn.jiiiiiin.security.core.authorize.AuthorizeConfigProvider;
import cn.jiiiiiin.security.rbac.component.service.RbacService;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhailiang
 */
@Component
@Order(Integer.MAX_VALUE)
public class RbacAuthorizeConfigProvider implements AuthorizeConfigProvider {

    /**
     * 自定义权限表达式，将会调用下面接口的方法进行权限判断
     *
     * @see RbacService#hasPermission(HttpServletRequest, Authentication)
     * @see cn.jiiiiiin.security.core.authorize.AuthorizeConfigProvider#config(org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry)
     */
    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config
                .anyRequest()
                //.authenticated();
                // 自定义权限表达式
                .access("@rbacService.hasPermission(request, authentication)");
        return true;
    }

}
