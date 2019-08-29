/**
 *
 */
package cn.jiiiiiin.security.browser.session;

import cn.jiiiiiin.security.core.properties.SecurityProperties;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 并发登录导致session失效时，默认的处理策略
 * <p>
 * 即登录用户被“踢掉”的处理
 *
 * @author zhailiang
 * @author jiiiiiin
 */
public class CustomExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

    public CustomExpiredSessionStrategy(SecurityProperties securityProperties) {
        super(securityProperties);
    }

    /**
     * @param event session超时事件，可以获取到“踢掉”用户的请求和响应对象
     *              在请求对象中，就可以获知是哪个登录导致当前登录用户被剔除等
     * @see org.springframework.security.web.session.SessionInformationExpiredStrategy#onExpiredSessionDetected(org.springframework.security.web.session.SessionInformationExpiredEvent)
     */
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        // 这里可以做日志的记录等操作
        onSessionInvalid(event.getRequest(), event.getResponse());
    }

    /* (non-Javadoc)
     * @see com.imooc.security.browser.session.AbstractSessionStrategy#isConcurrency()
     */
    @Override
    protected boolean isConcurrency() {
        return true;
    }

}
