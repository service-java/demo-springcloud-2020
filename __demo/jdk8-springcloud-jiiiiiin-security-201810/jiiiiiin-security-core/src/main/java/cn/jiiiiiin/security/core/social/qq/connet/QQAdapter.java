/**
 *
 */
package cn.jiiiiiin.security.core.social.qq.connet;

import cn.jiiiiiin.security.core.social.qq.api.QQ;
import cn.jiiiiiin.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 用来将服务提供商的个性化数据和spring social默认统一格式数据模型进行适配
 * <p>
 * 泛型参数标明需要适配的服务提供商数据类型api
 *
 * @author zhailiang
 */
public class QQAdapter implements ApiAdapter<QQ> {

    /**
     * 用来对服务提供商的服务进行心跳测试
     *
     * @param api
     * @return
     */
    @Override
    public boolean test(QQ api) {
        // 可以发请求对服务提供商接口进行探测
        return true;
    }

    /**
     * 数据适配，将个性化数据信息设置到标准结构
     * 需要在此设置 {@link Connection}所需要的数据项设置到values参数中
     *
     * @param api
     * @param values 包含了创建 {@link Connection}所需要的一些数据项
     */
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        final QQUserInfo userInfo = api.getUserInfo();
        // 显示的用户名
        values.setDisplayName(userInfo.getNickname());
        // 头像
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        // 个人主页url地址
        values.setProfileUrl(null);
        // 服务商的用户标识id
        values.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        // TODO Auto-generated method stub
        // TODO 待查询
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {
        // 用来想个人主页发一条最新消息，某些应用才可以玩
        //do noting
    }

}
