package cn.jiiiiiin.security.browser.component.authentication;


import cn.jiiiiiin.security.browser.utils.HttpUtils;
import cn.jiiiiiin.security.core.dict.CommonConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理接口
 * <p>
 * https://docs.spring.io/spring-security/site/docs/4.2.7.RELEASE/reference/htmlsingle/#form-login-flow-handling
 * <p>
 * 可以自定义实现`AuthenticationSuccessHandler`接口，或者继承其下的子类
 * <p>
 * `SavedRequestAwareAuthenticationSuccessHandler` 是spring默认的成功处理器，会将请求重定向到登录之前的【期望访问资源】接口
 *
 * @author jiiiiiin
 */
@Slf4j
public class BrowserAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    protected ObjectMapper objectMapper;

    /**
     * 登录成功之后被调用
     * <p>
     * authentication内容：
     * <p>
     * {
     * // 用户具有的权限
     * "authorities": [
     * {
     * "authority": "admin"
     * }
     * ],
     * // 包含认证请求的信息
     * "details": {
     * // 发送请求的客户端ip
     * "remoteAddress": "0:0:0:0:0:0:0:1",
     * // sessionId
     * "sessionId": "3020A8B92661126C8D63E8C92242EA00"
     * },
     * // 标识用户信息是否已经通过了身份认证
     * "authenticated": true,
     * // 该对象就是`UserDetailsService`返回的`UserDetails`具有的内容，不同的实现将会返回不同的数据，下面是默认的用户名密码登录的`UserDetails`
     * "principal": {
     * "password": null,
     * "username": "admin",
     * "authorities": [
     * {
     * "authority": "admin"
     * }
     * ],
     * "accountNonExpired": true,
     * "accountNonLocked": true,
     * "credentialsNonExpired": true,
     * "enabled": true
     * },
     * // 用户录入的密码（默认不会返回到前台）
     * "credentials": null,
     * // 用户名
     * "name": "admin"
     * }
     *
     * @param request
     * @param response
     * @param authentication 封装了登录用户的认证信息（发起认证的UserDetails、ip...）
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.debug("身份认证（登录）成功");
        final Device currentDevice = HttpUtils.resolveDevice(request);
        // 根据渠道返回不同的响应数据
        // 还有一种做法是根据客户端程序配置来指定响应数据格式：https://coding.imooc.com/lesson/134.html#mid=6866
        if (!currentDevice.isNormal()) {
            respJson(response, authentication);
        } else {
            // 默认是做重定向到登录之前的【期望访问资源】接口
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    protected void respJson(HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType(CommonConstants.CONTENT_TYPE_JSON);
        // 将authentication转换成json str输出
        response.getWriter().write(objectMapper.writeValueAsString(authentication));
    }
}
