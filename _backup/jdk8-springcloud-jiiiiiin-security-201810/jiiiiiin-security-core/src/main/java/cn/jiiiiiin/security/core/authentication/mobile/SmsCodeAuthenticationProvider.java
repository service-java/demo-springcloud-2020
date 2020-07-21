/**
 *
 */
package cn.jiiiiiin.security.core.authentication.mobile;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 短信登录验证逻辑
 * <p>
 * 由于短信验证码的验证在过滤器里已完成，这里直接读取用户信息即可。
 *
 * @author zhailiang
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    /**
     * 进行身份认证的逻辑校验
     * 通过{@link UserDetailsService}获取用户信息（待认证的），认证完毕之后（因为短信登录的认证被抽取为通用认证，在对应的短信验证码校验filter中已经完成）重新组装成一个认证通过的authentication token
     *
     * @see org.springframework.security.authentication.AuthenticationProvider#
     * authenticate(org.springframework.security.core.Authentication)
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;

        // 获取手机号进行查询
        UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());

        if (user == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user, user.getAuthorities());

        // copy请求详细信息
        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    /**
     * 提供给框架的 {@link AuthenticationManager} 管理，在对应的`Authentication Filter`需要进行身份认证的时候回调用manager开查找是否有支持当前请求的`Authentication Provider`
     * 就会调用当前Provider的这个方法判断是否支持对当前请求进行认证
     * <p>
     * 可以参考 {@link org.springframework.security.authentication.ProviderManager#authenticate(Authentication)} 中的处理逻辑
     *
     * @param authentication 这个就是请求认证的authentication token，如 {@link SmsCodeAuthenticationToken}
     * @see org.springframework.security.authentication.AuthenticationProvider#
     * supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

}
