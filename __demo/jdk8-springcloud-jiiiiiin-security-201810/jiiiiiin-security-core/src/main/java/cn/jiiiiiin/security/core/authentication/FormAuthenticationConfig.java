/**
 *
 */
package cn.jiiiiiin.security.core.authentication;

import cn.jiiiiiin.security.core.dict.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * 表单登录配置
 *
 * @author zhailiang
 */
@Component
public class FormAuthenticationConfig {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    public void configure(HttpSecurity http) throws Exception {
        http
            // 开启表单登录（指定身份认证的方式）
            // 下面这样配置就改变了默认的httpBasic认证方式，而提供一个登录页面
            .formLogin()
            // 配置自定义登录页面所在的接口，如`/signIn.html`，在需要登录的时候去访问的接口（渲染的页面）
            .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
            // 配置自定义登录交易请求接口（上面的登录页面提交表单之后登录接口），会被UsernamePasswordAuthenticationFilter所识别作为requiresAuthenticationRequestMatcher
            .loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
            // 配置自定义认证成功处理器
            .successHandler(authenticationSuccessHandler)
            // 配置自定义认证失败处理器
            .failureHandler(authenticationFailureHandler);
    }

}
