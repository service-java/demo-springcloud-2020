/**
 *
 */
package cn.jiiiiiin.security.core.social.qq.connet;


import cn.jiiiiiin.security.core.social.qq.api.QQ;
import cn.jiiiiiin.security.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * 下面是spring social的授权登录流程：
 * <p>
 * ![](https://ws2.sinaimg.cn/large/0069RVTdgy1fuqnpxoqo6j30wg0hr0v0.jpg)
 * <p>
 * 当前类就是流程中的 Service Provider的实现
 * <p>
 * 泛型需要的是Api的接口类型
 *
 * @author zhailiang
 * @author jiiiiiin
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private final String appId;

    // 以下两个链接：http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
    /**
     * 流程的第一步：获取Authorization Code 接口
     * 应用将用户引导到这个地址（授权提供服务），进行流程的第一步，获取授权码【Authorization Code】
     */
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    /**
     * 流程的第四步：通过Authorization Code获取Access Token接口
     */
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    /**
     * 构造service provider
     * @param appId 应用【用户名】
     * @param appSecret 应用【密码】
     */
    public QQServiceProvider(String appId, String appSecret) {
        // 这里使用默认的QQOAuth2Template，因为qq遵循oauth2协议，所以这里的前两个参数对应的就是qq的应用id和secret
        // 这两个值相当于应用接入qq开发平台的用户名和密码，qq用来标识一个第三方应用
        // 参考： http://wiki.connect.qq.com/%E5%87%86%E5%A4%87%E5%B7%A5%E4%BD%9C_oauth2-0

        // 第三个参数，对应上面流程的第一步，将用户导向到认证服务器的一个地址
        // 第四个参数，对应上面流程的第四步，即向认证服务器申请令牌的地址
        // 默认实现OAuth2Template
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    /* (non-Javadoc)
     * @see org.springframework.social.oauth2.AbstractOAuth2ServiceProvider#getApi(java.lang.String)
     */
    @Override
    public QQ getApi(String accessToken) {
        // 多实例
        return new QQImpl(accessToken, appId);
    }

}
