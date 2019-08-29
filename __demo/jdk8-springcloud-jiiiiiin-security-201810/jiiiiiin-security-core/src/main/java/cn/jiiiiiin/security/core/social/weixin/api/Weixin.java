/**
 *
 */
package cn.jiiiiiin.security.core.social.weixin.api;

/**
 * 微信API调用接口
 *
 * @author zhailiang
 */
public interface Weixin {

    /**
     * @param openId 微信的oauth流程在获取access token的时候就已经获得了openid
     */
    WeixinUserInfo getUserInfo(String openId);

}
