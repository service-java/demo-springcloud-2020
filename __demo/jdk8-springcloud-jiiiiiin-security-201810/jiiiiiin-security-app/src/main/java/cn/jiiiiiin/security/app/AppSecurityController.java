/**
 *
 */
package cn.jiiiiiin.security.app;

import cn.jiiiiiin.security.app.component.authentication.social.AppSingUpUtils;
import cn.jiiiiiin.security.core.dict.SecurityConstants;
import cn.jiiiiiin.security.core.social.SocialConfig;
import cn.jiiiiiin.security.core.social.SocialController;
import cn.jiiiiiin.security.core.social.support.SocialUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhailiang
 * @author jiiiiiin
 */
@RestController
public class AppSecurityController extends SocialController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private AppSingUpUtils appSingUpUtils;

    /**
     * 需要注册时跳到这里，返回401（需要进行用户认证）和用户信息给前端
     * <p>
     * 客户端在收到授权用户信息之后，需要回显一个客户端的注册或绑定页面，回显授权用户信息
     *
     * @param request
     * @return 第三方授权用户信息
     * @see AppSingUpUtils
     * @see cn.jiiiiiin.security.app.component.authentication.social.SpringSocialConfigurerPostProcessor
     * @see SocialConfig#socialSecurityConfig()
     */
    @GetMapping(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        // 在当前请求[第三方]通过重定向回到应用的时候，用户信息可以放到session，但下一次请求会新建一个session（token模式）
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        // 故需要进行缓存
        appSingUpUtils.saveConnectionData(new ServletWebRequest(request), connection.createData());
        return buildSocialUserInfo(connection);
    }

}
