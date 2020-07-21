/**
 *
 */
package cn.jiiiiiin.security.core.social.qq.connet;

import cn.jiiiiiin.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * 参考：
 * ![](https://ws2.sinaimg.cn/large/0069RVTdgy1fuqp901tqxj30y20izwgv.jpg)
 *
 * 用户创建 {@link org.springframework.social.connect.Connection}
 *
 * @author zhailiang
 * @author jiiiiiin
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    /**
     * @param providerId 服务提供商的唯一标识
     * @param appId      服务提供商下发给应用的应用id
     * @param appSecret  服务提供商下发给应用的应用密码
     */
    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }

}
