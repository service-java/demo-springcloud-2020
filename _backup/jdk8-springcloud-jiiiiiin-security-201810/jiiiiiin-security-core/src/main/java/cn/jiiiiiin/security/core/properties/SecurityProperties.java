package cn.jiiiiiin.security.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jiiiiiin
 * @ConfigurationProperties(prefix = "jiiiiiin.security") 将会读取配置文件中对应prefix的配置项
 */
@ConfigurationProperties(prefix = "jiiiiiin.security")
@Setter
@Getter
public class SecurityProperties {
    /**
     * 浏览器环境配置
     */
    private BrowserProperties browser = new BrowserProperties();
    /**
     * 验证码配置
     */
    private ValidateCodeProperties validate = new ValidateCodeProperties();

    /**
     * 社交登录配置
     */
    private SocialProperties social = new SocialProperties();

    /**
     * OAuth2认证服务器配置
     */
    private OAuth2Properties oauth2 = new OAuth2Properties();
}
