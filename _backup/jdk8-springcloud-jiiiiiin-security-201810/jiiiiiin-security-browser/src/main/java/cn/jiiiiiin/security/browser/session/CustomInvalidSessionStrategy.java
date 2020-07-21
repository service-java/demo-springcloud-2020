/**
 *
 */
package cn.jiiiiiin.security.browser.session;

import cn.jiiiiiin.security.core.properties.SecurityProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认的session失效处理策略
 *
 * @author zhailiang
 * @author jiiiiiin
 * @see HttpSecurity#sessionManagement()#invalidSessionStrategy
 */
public class CustomInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

    public CustomInvalidSessionStrategy(SecurityProperties securityProperties) {
        super(securityProperties);
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        onSessionInvalid(request, response);
    }

}
