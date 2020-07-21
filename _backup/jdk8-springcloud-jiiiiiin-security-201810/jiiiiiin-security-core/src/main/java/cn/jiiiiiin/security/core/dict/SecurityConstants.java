/**
 *
 */
package cn.jiiiiiin.security.core.dict;

import cn.jiiiiiin.security.core.social.SocialConfig;
import cn.jiiiiiin.security.core.validate.code.ValidateCodeRepository;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author zhailiang
 */
public interface SecurityConstants {

    /**
     * 默认的处理验证码的url前缀
     */
    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";
    /**
     * 当请求需要身份认证时，默认跳转的url
     */
    String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";
    /**
     * 默认的用户名密码登录请求处理url
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_FORM = "/authentication/form";
    /**
     * 默认的手机验证码登录请求处理url
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE = "/authentication/mobile";
    /**
     * 默认的OPENID登录请求处理url
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_OPENID = "/authentication/openid";
    /**
     * 默认登录页面
     */
    String DEFAULT_SIGN_IN_PAGE_URL = "/signIn";
    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";
    /**
     * 请求验证码(token认证模式)时候需要的客户端标识
     * @see ValidateCodeRepository 的实现
     */
    String DEFAULT_PARAMETER_NAME_DEVICEID = "deviceId";
    /**
     * 请求验证码接口时候可选参数，用于动态设置验证码超时参数
     */
    String DEFAULT_PARAMETER_NAME_EXPIRE_IN = "expireIn";
    /**
     * openid参数名
     */
    String DEFAULT_PARAMETER_NAME_OPENID = "openId";
    /**
     * providerId参数名
     */
    String DEFAULT_PARAMETER_NAME_PROVIDERID = "providerId";
    /**
     * session失效默认的跳转地址
     *
     * @see org.springframework.security.config.annotation.web.builders.HttpSecurity#sessionManagement#invalidSessionUrl
     */
    String DEFAULT_SESSION_INVALID_URL = "/sessionInvalid";
    /**
     * 获取第三方用户信息的url
     *
     * 针对 app模块的针对{@link SpringSocialConfigurer#signupUrl(String)}的处理器
     *      * @see SocialConfig#socialSecurityConfig()
     */
    String DEFAULT_SOCIAL_USER_INFO_URL = "/social/admin";

    /**
     * social 第三方授权用户管理表的表前缀
     */
    String SOCIAL_TABLE_PREFIX = "springsocial_";
    /**
     * 默认退出登录的处理接口
     */
    String LOGOUT_URL = "/signOut";

    /**
     * 授权匹配时候需要放开的静态资源：js
     */
    String STATIC_RESOURCES_JS = "/js/**";
}
