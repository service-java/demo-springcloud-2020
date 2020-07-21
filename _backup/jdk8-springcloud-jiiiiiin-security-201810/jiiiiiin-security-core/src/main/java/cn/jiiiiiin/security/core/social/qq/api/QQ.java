/**
 *
 */
package cn.jiiiiiin.security.core.social.qq.api;

/**
 * @author zhailiang
 */
public interface QQ {

    /**
     * 获取用户信息
     * <p>
     * oauth第六步流程通过access_token请求获取qq用户信息接口
     * <p>
     * http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
     *
     * @return
     */
    QQUserInfo getUserInfo();

}
