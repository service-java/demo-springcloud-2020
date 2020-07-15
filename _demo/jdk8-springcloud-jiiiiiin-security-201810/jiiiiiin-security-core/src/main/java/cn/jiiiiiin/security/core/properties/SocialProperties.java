/**
 *
 */
package cn.jiiiiiin.security.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * 社交登录配置项
 *
 * @author zhailiang
 */
@Setter
@Getter
public class SocialProperties {

    /**
     * 社交登录功能拦截的url
     *
     * @see SocialAuthenticationFilter#DEFAULT_FILTER_PROCESSES_URL
     */
    private String filterProcessesUrl = "/auth";

    private QQProperties qq = new QQProperties();

	private WeixinProperties weixin = new WeixinProperties();

}
