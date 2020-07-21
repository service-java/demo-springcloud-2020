/**
 *
 */
package cn.jiiiiiin.security.core.authentication.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * 短信登录验证信息封装类
 * <p>
 * 类{@link org.springframework.security.authentication.UsernamePasswordAuthenticationToken} 的作用
 * <p>
 * 身份认证成功之前，存储的是待认证的客户手机号
 * <p>
 * 认证成功之后，存储的是认证之后的用户信息
 *
 * @author zhailiang
 * @author jiiiiiin
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 存放认证信息，这里是
     * <p>
     * 认证之前存放手机号
     * <p>
     * 认证之后存储登录用户信息
     */
    private final Object principal;

    // 可以用来存储认证“凭据”密码，在这里手机验证码放在前置短信验证码过滤器中完成验证，故这里不需要
    //private Object credentials;


    /**
     * 认证之前调用
     * <p>
     * This constructor can be safely used by any code that wishes to create a
     * <code>SmsCodeAuthenticationToken</code>, as the {@link #isAuthenticated()}
     * will return <code>false</code>.
     *
     * @param mobile 认证凭证
     */
    public SmsCodeAuthenticationToken(String mobile) {
        super(null);
        this.principal = mobile;
        setAuthenticated(false);
    }

    /**
     * 认证之后调用
     * <p>
     * This constructor should only be used by <code>AuthenticationManager</code> or
     * <code>AuthenticationProvider</code> implementations that are satisfied with
     * producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>)
     * authentication token.
     *
     * @param principal   认证之后的用户信息
     * @param authorities 用户具有的权限集合
     */
    public SmsCodeAuthenticationToken(Object principal,
                                      Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true); // must use super, as we override
    }

    @Deprecated
    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Deprecated
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
