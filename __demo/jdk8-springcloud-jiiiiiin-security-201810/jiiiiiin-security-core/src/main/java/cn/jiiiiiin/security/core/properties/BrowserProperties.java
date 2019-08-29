package cn.jiiiiiin.security.core.properties;

import cn.jiiiiiin.security.core.dict.SecurityConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.social.oauth2.OAuth2Parameters;

/**
 * @author jiiiiiin
 */
@Setter
@Getter
public class BrowserProperties {

    /**
     * session管理配置项
     */
    private SessionProperties session = new SessionProperties();

    /**
     * 身份认证（登录）页面
     * 默认页面："/signIn.html"
     */
    private String signInUrl = SecurityConstants.DEFAULT_SIGN_IN_PAGE_URL;

    /**
     * 社交登录，如果需要用户注册，跳转的页面
     */
    private String signUpUrl = "/signUp.html";

    /**
     * 退出成功时跳转的html输出页面，如果是移动端渠道访问则返回json数据，不走该页面。
     */
    private String signOutUrl = "/signOut.html";

    /**
     * '记住我'功能的有效时间，单位（秒），默认1小时
     */
    private int rememberMeSeconds = 3600;

    /**
     * 代理地址，用于访问第三方授权服务标识自身应用的服务器地址
     * 防止 {@link cn.jiiiiiin.security.core.social.weixin.connect.WeixinOAuth2Template#buildAuthenticateUrl(OAuth2Parameters)} 调用基类返回的其实是一个本地测试地址的情况
     */
    private String proxyUri;

}
