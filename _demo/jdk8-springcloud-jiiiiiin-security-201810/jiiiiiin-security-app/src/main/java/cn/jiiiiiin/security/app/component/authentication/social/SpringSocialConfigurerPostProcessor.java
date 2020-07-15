package cn.jiiiiiin.security.app.component.authentication.social;

import cn.jiiiiiin.security.core.dict.SecurityConstants;
import cn.jiiiiiin.security.core.social.SocialConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 新增setSocialAuthenticationFilterPostProcessor设置，通过socialAuthenticationFilterPostProcessor自定义后处理器，来控制获取到第三方用户信息，但是没有用户标识（UserConnnect表中没有记录），让socialAuthenticationFilterPostProcessor帮我们将授权和客户端标识通过AppSingUpUtils(app环境下替换providerSignInUtils，避免由于没有session导致读不到社交用户信息的问题)将客户端和授权信息缓存到本地，在返回给客户端json格式的用户信息；
 * <p>
 * 实现{@link BeanPostProcessor}会在ioc容器中对应的每一个bean完成初始化前后都会调用定义的接口
 * <p>
 * 处理{@link SocialConfig#socialSecurityConfig()}这个bean
 *
 * @author jiiiiiin
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * @see cn.jiiiiiin.security.app.AppSecurityController#getSocialUserInfo(HttpServletRequest)
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // socialSecurityConfig就是需要进行后处理的bean
        if (StringUtils.equals(beanName, "socialSecurityConfig")) {
            // 实现`CustomSpringSocialConfigurer`
            SpringSocialConfigurer springSocialConfigurer = (SpringSocialConfigurer) bean;
            // 覆盖默认的针对浏览器的处理接口
            // @see cn.jiiiiiin.security.app.AppSecurityController#getSocialUserInfo(HttpServletRequest)
            springSocialConfigurer.signupUrl(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL);
            return springSocialConfigurer;
        }
        return bean;
    }
}
