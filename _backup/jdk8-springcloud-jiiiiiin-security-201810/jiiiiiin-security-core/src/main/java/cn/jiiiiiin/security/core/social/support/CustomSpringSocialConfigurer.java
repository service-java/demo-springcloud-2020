/**
 *
 */
package cn.jiiiiiin.security.core.social.support;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 继承默认的社交登录配置，加入自定义的后处理逻辑
 * <p>
 * 修改{@link SocialAuthenticationFilter#DEFAULT_FILTER_PROCESSES_URL}这个的默认配置
 *
 * @author zhailiang
 */
public class CustomSpringSocialConfigurer extends SpringSocialConfigurer {

    /**
     * @see CustomSpringSocialConfigurer#postProcess(Object)
     */
    private String filterProcessesUrl;

    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    public CustomSpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    /**
     * 该方法将会在{@link SpringSocialConfigurer#configure}实例化{@link SocialAuthenticationFilter}的时候被调用，可以用来进行SocialAuthenticationFilter的定制化
     * <p>
     * 默认 {@link SocialAuthenticationFilter}处理的接口是以 {@link SocialAuthenticationFilter#DEFAULT_FILTER_PROCESSES_URL}开通的，下面为了自定义这个属性，
     * 我们需要实现当前方法，进行定制，可能有这种需求
     *
     * @param object {@link SocialAuthenticationFilter}对象
     * @see org.springframework.security.config.annotation.SecurityConfigurerAdapter#postProcess(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected <T> T postProcess(T object) {
        final SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
        if (socialAuthenticationFilterPostProcessor != null) {
            // 处理获取到第三方用户信息之后的处理
            socialAuthenticationFilterPostProcessor.process(filter);
        }
        return (T) filter;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    public SocialAuthenticationFilterPostProcessor getSocialAuthenticationFilterPostProcessor() {
        return socialAuthenticationFilterPostProcessor;
    }

    public void setSocialAuthenticationFilterPostProcessor(
            SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor) {
        this.socialAuthenticationFilterPostProcessor = socialAuthenticationFilterPostProcessor;
    }

}
