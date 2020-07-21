/**
 *
 */
package cn.jiiiiiin.security.core.authentication.mobile;

import cn.jiiiiiin.security.core.dict.SecurityConstants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 短信登录过滤器
 *
 * @author zhailiang
 * @author jiiiiiin
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 需要获取的认证凭证
     * <p>
     * 以手机号作为凭证
     */
    private String mobilePhoneParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
    /**
     * 标识当前过滤器只处理post方式的请求
     */
    private boolean postOnly = true;

    /**
     * 配置当前过滤器需要处理那些请求，默认处理{@link SecurityConstants#DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE}
     */
    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE, "POST"));
    }

    /**
     * 认证流程
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (postOnly && !"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String mobile = obtainMobilePhone(request);

        if (mobile == null) {
            mobile = "";
        }

        mobile = mobile.trim();

        // 实例化认证token
        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);

        // Allow subclasses to set the "details" property
        // 将请求信息设置到token中
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }


    /**
     * 获取手机号
     */
    protected String obtainMobilePhone(HttpServletRequest request) {
        return request.getParameter(mobilePhoneParameter);
    }

    /**
     * 将请求的ip、sessionId等设置到token中
     * <p>
     * Provided so that subclasses may configure what is put into the
     * authentication request's details property.
     *
     * @param request     that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     *                    set
     */
    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Sets the parameter name which will be used to obtain the username from
     * the login request.
     *
     * @param mobilePhoneParameter the parameter name. Defaults to {@link SecurityConstants#DEFAULT_PARAMETER_NAME_MOBILE}.
     */
    public void setMobilePhoneParameter(String mobilePhoneParameter) {
        Assert.hasText(mobilePhoneParameter, "Username parameter must not be empty or null");
        this.mobilePhoneParameter = mobilePhoneParameter;
    }


    /**
     * Defines whether only HTTP POST requests will be allowed by this filter.
     * If set to true, and an authentication request is received which is not a
     * POST request, an exception will be raised immediately and authentication
     * will not be attempted. The <tt>unsuccessfulAuthentication()</tt> method
     * will be called as if handling a failed authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobilePhoneParameter() {
        return mobilePhoneParameter;
    }

}
