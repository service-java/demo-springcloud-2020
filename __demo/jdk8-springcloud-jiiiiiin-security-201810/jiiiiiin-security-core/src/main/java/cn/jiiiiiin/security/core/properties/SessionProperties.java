/**
 *
 */
package cn.jiiiiiin.security.core.properties;

import cn.jiiiiiin.security.core.dict.SecurityConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * session管理相关配置项
 *
 * @author zhailiang
 * @author jiiiiiin
 */
@Setter
@Getter
public class SessionProperties {

    /**
     * 同一个用户在系统中的最大session数，默认1
     *
     * @see HttpSecurity#sessionManagement() {@link #maximumSessions}
     */
    private int maximumSessions = 1;
    /**
     * 达到最大session时是否阻止新的登录请求，默认为false，不阻止，新的登录会将老的登录失效掉
     *
     * @see HttpSecurity#sessionManagement() {@link #maxSessionsPreventsLogin}
     */
    private boolean maxSessionsPreventsLogin;
    /**
     * session失效时跳转的地址
     *
     * @see HttpSecurity#sessionManagement() {@link #sessionInvalidUrl}
     */
    private String sessionInvalidUrl = SecurityConstants.DEFAULT_SESSION_INVALID_URL;

}
