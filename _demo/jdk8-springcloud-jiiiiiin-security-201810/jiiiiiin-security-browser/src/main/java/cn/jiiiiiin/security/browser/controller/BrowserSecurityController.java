package cn.jiiiiiin.security.browser.controller;

import cn.jiiiiiin.security.core.dict.CommonConstants;
import cn.jiiiiiin.security.core.dict.SecurityConstants;
import cn.jiiiiiin.security.core.properties.SecurityProperties;
import cn.jiiiiiin.security.core.social.SocialController;
import cn.jiiiiiin.security.core.social.support.SocialUserInfo;
import cn.jiiiiiin.security.core.support.SimpleResponse;
import lombok.val;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mobile.device.Device;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jiiiiiin:
 * security:
 * browser:
 * loginPage = /demo-signIn.html
 *
 * @author jiiiiiin
 */
@RestController
public class BrowserSecurityController extends SocialController {

    final static Logger L = LoggerFactory.getLogger(BrowserSecurityController.class);

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 当框架认为需要进行身份认证，会将请求缓存到requestCache中，这里我们在登录完成之后，从这个对象中拿出框架帮我们缓存的上一个被拦截的请求
     */
    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /**
     * 即当需要身份认证时候需要访问该接口，该接口负责根据渠道去渲染（身份认证（登录）页面）或返回json提示
     * <p>
     * 对应spring security的`.loginPage([])`配置
     * 将区分请求的渠道进行不同的响应
     *
     * @param request
     * @param response
     * @return
     * @ResponseStatus(code = HttpStatus.UNAUTHORIZED) 返回的状态码标识返回给非网页版客户端，标识需要进行用户授权
     */
    @RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response, Device device, Model model) throws IOException {
        if (device.isNormal()) {
            // 获取到上一个被拦截的请求(原始请求）
            final SavedRequest savedRequest = requestCache.getRequest(request, response);
            if (savedRequest != null) {
                final String transTarget = savedRequest.getRedirectUrl();
                L.info("需要进行身份认证的请求是 {}", transTarget);
            }
            var msg = request.getParameter(WebAttributes.AUTHENTICATION_EXCEPTION);
            // 直接跳转到登录页面
            L.info("跳转到身份认证页面 {} {}", securityProperties.getBrowser().getSignInUrl(), msg);
            if(StringUtils.isEmpty(msg)){
                msg = "访问的服务需要身份认证";
            }
            val url = securityProperties.getBrowser().getSignInUrl()+ "?" + WebAttributes.AUTHENTICATION_EXCEPTION + "=" + msg;
            redirectStrategy.sendRedirect(request, response, url);
            return null;
        }else {
            return SimpleResponse.newInstance("访问的服务需要身份认证");
        }
    }

    /**
     * 用户第一次社交登录时，会引导用户进行用户注册或绑定，此服务用于在注册或绑定页面获取社交网站用户信息
     *
     * @param request
     * @return
     */
    @GetMapping(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        // 从session中获取连接对象，在获取用户信息
        // @see Connection
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        return buildSocialUserInfo(connection);
    }

//    /**
//     * ss权限配置中`invalidSessionUrl`配置
//     * @param request
//     * @param response
//     * @param device
//     * @return
//     * @throws IOException
//     */
//    @RequestMapping(INVALID_SESSION_URL)
//    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
//    public SimpleResponse requireAuthenticationOnInvalidSession(HttpServletRequest request, HttpServletResponse response, Device device, Model entity) throws IOException {
//        // 获取到上一个被拦截的请求(原始请求）
//        final SavedRequest savedRequest = requestCache.getRequest(request, response);
//        if (savedRequest != null) {
//            final String transTarget = savedRequest.getRedirectUrl();
//            L.info("session 失效，需要进行身份认证的请求是 {}", transTarget);
//            // 检测请求是否是以html结尾，我们就认为是访问网页版本
//            // if(StringUtils.endsWithIgnoreCase(transTarget, ".html")){
//            // 借助spring mobile来区分渠道
//            if (device.isNormal()) {
//                // 直接跳转到登录页面
//                L.info("跳转到身份认证页面 {}", securityProperties.getBrowser().getSignInUrl());
//                entity.addAttribute(MODEL_KEY_HINT_MSG, "登录会话失效，访问的服务需要重新身份认证");
//                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getSignInUrl());
//                return null;
//            }
//        }
//        return SimpleResponse.newInstance("登录会话失效，访问的服务需要重新身份认证");
//    }
}
