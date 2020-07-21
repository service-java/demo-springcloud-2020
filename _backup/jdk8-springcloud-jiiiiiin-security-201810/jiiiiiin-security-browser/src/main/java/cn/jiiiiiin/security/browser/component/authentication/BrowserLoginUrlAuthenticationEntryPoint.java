package cn.jiiiiiin.security.browser.component.authentication;

import cn.jiiiiiin.security.browser.utils.HttpUtils;
import lombok.val;
import lombok.var;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * > [自定义AuthenticationEntryPoint](https://segmentfault.com/a/1190000012137647)
 * {@link org.springframework.security.web.access.ExceptionTranslationFilter#authenticationEntryPoint}
 *
 * @author jiiiiiin
 */
public class BrowserLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    /**
     * @param loginFormUrl URL where the login page can be found. Should either be
     *                     relative to the web-app context path (include a leading {@code /}) or an absolute
     *                     URL.
     */
    public BrowserLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        val currentDevice = HttpUtils.resolveDevice(request);
        // 根据渠道返回不同的响应数据
        if (!currentDevice.isNormal()) {
            // TODO 待测试
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        } else {
            super.commence(request, response, authException);
        }
    }

    @Override
    protected String buildRedirectUrlToLoginPage(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        var res = super.buildRedirectUrlToLoginPage(request, response, authException);
        res += "?" + WebAttributes.AUTHENTICATION_EXCEPTION + "=" + authException.getMessage();
        return res;
    }
}
