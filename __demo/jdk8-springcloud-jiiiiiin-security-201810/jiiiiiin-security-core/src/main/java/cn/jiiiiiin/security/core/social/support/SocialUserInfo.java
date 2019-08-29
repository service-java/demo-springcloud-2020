/**
 *
 */
package cn.jiiiiiin.security.core.social.support;

/**
 * @author zhailiang
 */
public class SocialUserInfo {

    /**
     * 第三方授权服务提供商标识
     */
    private String providerId;
    /**
     * 第三方授权服务用户唯一标识
     */
    private String providerUserId;
    /**
     * 第三方授权服务用户昵称
     */
    private String nickname;
    /**
     * 第三方授权服务用户头像
     */
    private String headimg;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

}
