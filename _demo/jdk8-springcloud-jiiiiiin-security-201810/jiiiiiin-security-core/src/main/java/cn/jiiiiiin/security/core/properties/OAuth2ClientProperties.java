/**
 *
 */
package cn.jiiiiiin.security.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;

/**
 * 认证服务器注册的第三方应用配置项
 *
 * 可以自行添加需要配置的属性{@link ClientDetailsServiceBuilder.ClientBuilder}
 *
 * @author zhailiang
 */
@Setter
@Getter
public class OAuth2ClientProperties {

    /**
     * 第三方应用appId
     */
    private String clientId;
    /**
     * 第三方应用appSecret
     */
    private String clientSecret;
    /**
     * 针对此应用发出的token的有效时间
     */
    private int accessTokenValidateSeconds = 7200;

}
